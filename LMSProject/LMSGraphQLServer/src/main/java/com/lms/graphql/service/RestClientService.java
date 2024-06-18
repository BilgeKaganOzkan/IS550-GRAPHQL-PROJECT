package com.lms.graphql.service;

import com.lms.graphql.variables.inputs.AddBookInput;
import com.lms.graphql.variables.inputs.BorrowBookInput;
import com.lms.graphql.variables.inputs.LoginInput;
import com.lms.graphql.variables.inputs.ReturnBookInput;
import com.lms.graphql.variables.returns.Book;
import com.lms.graphql.variables.returns.ReturnType;
import com.lms.graphql.variables.returns.UserBorrowingInfos;
import com.lms.graphql.variables.returns.UserLoginInfos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RestClientService {

    @Autowired
    private RestTemplate restTemplate;

    private final String BASE_URL = "http://localhost:9090";

    private HttpHeaders createHeaders(Long loginId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Login-ID", loginId.toString());
        return headers;
    }

    public List<Book> searchBooks(String name, String author, Long loginId) {
        String url = BASE_URL + "/book/" + (!Objects.equals(name, "") ? name : "null") + "/" + (!Objects.equals(author, "") ? author : "null");
        HttpEntity<String> entity = new HttpEntity<>(createHeaders(loginId));
        ResponseEntity<CollectionModel<EntityModel<Book>>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<CollectionModel<EntityModel<Book>>>() {});
        return response.getBody().getContent()
                .stream()
                .map(EntityModel::getContent)
                .collect(Collectors.toList());
    }

    public List<UserBorrowingInfos> getUserBorrowingInfos(Long studentId, Long loginId) {
        String url = BASE_URL + "/user/info/" + studentId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders(loginId));
        ResponseEntity<CollectionModel<EntityModel<UserBorrowingInfos>>> response = restTemplate.exchange(url, HttpMethod.GET, entity,  new ParameterizedTypeReference<CollectionModel<EntityModel<UserBorrowingInfos>>>() {});
        return response.getBody().getContent()
                .stream()
                .map(EntityModel::getContent)
                .collect(Collectors.toList());
    }

    public Book getBookById(Long bookId, Long loginId) {
        String url = BASE_URL + "/book/" + bookId;
        HttpEntity<?> entity = new HttpEntity<>(createHeaders(loginId));
        ResponseEntity<EntityModel<Book>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<EntityModel<Book>>() {});
        return response.getBody().getContent();
    }

    public UserLoginInfos login(LoginInput loginRequest) {
        String url = BASE_URL + "/login";
        ResponseEntity<UserLoginInfos> response = restTemplate.postForEntity(url, loginRequest, UserLoginInfos.class);
        return response.getBody();
    }

    public ReturnType addBorrowBook(BorrowBookInput borrowBook, Long loginId) {
        String url = BASE_URL + "/book/borrow";
        HttpEntity<BorrowBookInput> entity = new HttpEntity<>(borrowBook, createHeaders(loginId));
        ResponseEntity<EntityModel<ReturnType>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<EntityModel<ReturnType>>() {});
        return response.getBody().getContent();
    }

    public ReturnType addReturnBook(ReturnBookInput returnBook, Long loginId) {
        String url = BASE_URL + "/book/return";
        HttpEntity<ReturnBookInput> entity = new HttpEntity<>(returnBook, createHeaders(loginId));
        ResponseEntity<EntityModel<ReturnType>> response = restTemplate.exchange(url, HttpMethod.PUT, entity, new ParameterizedTypeReference<EntityModel<ReturnType>>() {});
        return response.getBody().getContent();
    }

    public ReturnType addBook(AddBookInput addBook, Long loginId) {
        String url = BASE_URL + "/book/add";
        HttpEntity<AddBookInput> entity = new HttpEntity<>(addBook, createHeaders(loginId));
        ResponseEntity<EntityModel<ReturnType>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<EntityModel<ReturnType>>() {});
        return response.getBody().getContent();
    }

    public ReturnType deleteBook(Long bookId, Long loginId) {
        String url = BASE_URL + "/book/" + bookId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders(loginId));
        ResponseEntity<EntityModel<ReturnType>> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, new ParameterizedTypeReference<EntityModel<ReturnType>>() {});
        return response.getBody().getContent();
    }
}