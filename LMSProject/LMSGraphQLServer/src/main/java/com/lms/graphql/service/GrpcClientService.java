package com.lms.graphql.service;

import com.lms.graphql.variables.inputs.*;
import com.lms.graphql.variables.returns.UserID;
import com.lms.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {

    private final AdminServiceGrpc.AdminServiceBlockingStub blockingStub;
    
    public GrpcClientService() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        blockingStub = AdminServiceGrpc.newBlockingStub(channel);
    }

    public UserID addUser(AddUserInput input){
        AddUserRequest request = AddUserRequest.newBuilder()
                .setLoginID(input.getLoginID())
                .setUser(graphqlUserTogrpcUser(input.getUser()))
                .build();
        AddUserResponse response = blockingStub.addUser(request);
        UserID userId = new UserID();
        userId.setUserID(response.getUserID());
        return userId;
    }

    public com.lms.graphql.variables.returns.GenericResponse deleteUser(DeleteUserInput input){
        DeleteUserRequest request = DeleteUserRequest.newBuilder()
                .setLoginID(input.getLoginID())
                .setUserID(input.getUserID())
                .build();
        com.lms.grpc.GenericResponse response = blockingStub.deleteUser(request);
        com.lms.graphql.variables.returns.GenericResponse genericResponse = new com.lms.graphql.variables.returns.GenericResponse();
        genericResponse.setMessage(response.getMessage());
        return genericResponse;
    }

    public com.lms.graphql.variables.returns.GenericResponse changePassword(ChangePasswordInput input){
        ChangePasswordRequest request = ChangePasswordRequest.newBuilder()
                .setLoginID(input.getLoginID())
                .setOldPassword(input.getOldPassword())
                .setNewPassword(input.getNewPassword())
                .build();
        com.lms.grpc.GenericResponse response = blockingStub.changePassword(request);
        com.lms.graphql.variables.returns.GenericResponse genericResponse = new com.lms.graphql.variables.returns.GenericResponse();
        genericResponse.setMessage(response.getMessage());
        return genericResponse;
    }

    public com.lms.graphql.variables.returns.GenericResponse updateContactInfo(UpdateContactInfoInput input){
        UpdateContactInfoRequest request = UpdateContactInfoRequest.newBuilder()
                .setLoginID(input.getLoginID())
                .setNewLocation(input.getNewLocation())
                .setNewTelNumber(input.getNewTelNumber())
                .build();
        com.lms.grpc.GenericResponse response = blockingStub.updateContactInfo(request);
        com.lms.graphql.variables.returns.GenericResponse genericResponse = new com.lms.graphql.variables.returns.GenericResponse();
        genericResponse.setMessage(response.getMessage());
        return genericResponse;
    }

    public com.lms.graphql.variables.returns.GenericResponse updateUserInfo(UpdateUserInfoInput input){
        UpdateUserInfoRequest request = UpdateUserInfoRequest.newBuilder()
                .setLoginID(input.getLoginID())
                .setUserID(input.getUserID())
                .setUser(graphqlUserTogrpcUser(input.getUser()))
                .build();
        com.lms.grpc.GenericResponse response = blockingStub.updateUserInfo(request);
        com.lms.graphql.variables.returns.GenericResponse genericResponse = new com.lms.graphql.variables.returns.GenericResponse();
        genericResponse.setMessage(response.getMessage());
        return genericResponse;
    }

    public com.lms.graphql.variables.returns.User getUserInfo(int loginID, int userID) {
        GetUserInfoRequest request = GetUserInfoRequest.newBuilder()
                .setLoginID(loginID)
                .setUserID(userID)
                .build();
        GetUserInfoResponse response = blockingStub.getUserInfo(request);
        com.lms.graphql.variables.returns.User user = grpcUserTo(response.getUser());
        return user;
    }
    
    public com.lms.grpc.User graphqlUserTogrpcUser(com.lms.graphql.variables.inputs.UserInput input) {
        com.lms.grpc.User user = com.lms.grpc.User.newBuilder()
                .setStudentID(input.getStudentID())
                .setName(input.getName())
                .setSurname(input.getSurname())
                .setEmail(input.getEmail())
                .setPassword(input.getPassword())
                .setDepartment(input.getDepartment())
                .setLocation(input.getLocation())
                .setTelNumber(input.getTelNumber())
                .setType(input.getType() == com.lms.graphql.variables.enums.UserType.ADMIN ? com.lms.grpc.UserType.ADMIN :
                        input.getType() == com.lms.graphql.variables.enums.UserType.LIBRARIAN ? com.lms.grpc.UserType.LIBRARIAN :
                                com.lms.grpc.UserType.USER)
                .build();
        return user;
    }
    
    public com.lms.graphql.variables.returns.User grpcUserTo(com.lms.grpc.User input) {
        com.lms.graphql.variables.returns.User user = new com.lms.graphql.variables.returns.User();
        user.setStudentID(input.getStudentID());
        user.setName(input.getName());
        user.setSurname(input.getSurname());
        user.setEmail(input.getEmail());
        user.setPassword(input.getPassword());
        user.setDepartment(input.getDepartment());
        user.setLocation(input.getLocation());
        user.setTelNumber(input.getTelNumber());

        if(input.getType() == com.lms.grpc.UserType.ADMIN) {
            user.setType(com.lms.graphql.variables.enums.UserType.ADMIN);
        }
        else if (input.getType() == com.lms.grpc.UserType.LIBRARIAN) {
            user.setType(com.lms.graphql.variables.enums.UserType.LIBRARIAN);
        }
        else {
            user.setType(com.lms.graphql.variables.enums.UserType.USER);
        }

        return user;
    }
}