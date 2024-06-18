package com.lms.graphql.exception;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GenericGraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CustomGraphQLExceptionHandler implements GraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        return errors.stream()
                .map(this::toCustomError)
                .collect(Collectors.toList());
    }

    private GraphQLError toCustomError(GraphQLError error) {
        if (error instanceof ExceptionWhileDataFetching) {
            ExceptionWhileDataFetching exceptionError = (ExceptionWhileDataFetching) error;
            Throwable cause = exceptionError.getException();

            // Handle REST API errors
            if (cause instanceof HttpClientErrorException) {
                HttpClientErrorException restException = (HttpClientErrorException) cause;
                return new GenericGraphQLError("REST API Error: " + restException.getMessage()) {
                    @Override
                    public Map<String, Object> getExtensions() {
                        return Map.of(
                                "statusCode", restException.getStatusCode().value(),
                                "responseBody", restException.getResponseBodyAsString()
                        );
                    }
                };
            } else if (cause instanceof HttpServerErrorException) {
                HttpServerErrorException restException = (HttpServerErrorException) cause;
                return new GenericGraphQLError("REST API Error: " + restException.getMessage()) {
                    @Override
                    public Map<String, Object> getExtensions() {
                        return Map.of(
                                "statusCode", restException.getStatusCode().value(),
                                "responseBody", restException.getResponseBodyAsString()
                        );
                    }
                };
            }

            // Handle gRPC errors
            else if (cause instanceof StatusRuntimeException) {
                StatusRuntimeException grpcException = (StatusRuntimeException) cause;
                return new GenericGraphQLError("gRPC Error: " + grpcException.getStatus().getDescription()) {
                    @Override
                    public Map<String, Object> getExtensions() {
                        return Map.of(
                                "statusCode", grpcException.getStatus().getCode().value(),
                                "description", grpcException.getStatus().getDescription()
                        );
                    }
                };
            }
        }
        return error;
    }
}