package com.lms.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.lms.graphql.service.GrpcClientService;
import com.lms.graphql.service.RestClientService;
import com.lms.graphql.variables.inputs.LoginInput;
import com.lms.graphql.variables.returns.Book;
import com.lms.graphql.variables.returns.UserBorrowingInfos;
import com.lms.graphql.variables.returns.UserLoginInfos;
import com.lms.graphql.variables.returns.User;
import com.lms.grpc.GetUserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    private RestClientService libraryService;

    @Autowired
    private GrpcClientService adminService;

    public List<Book> getSearchedBooks(String name, String author, Long loginId) {
        return libraryService.searchBooks(name, author, loginId);
    }

    public List<UserBorrowingInfos> getUserBorrowingInfos(Long studentId, Long loginId) {
        return libraryService.getUserBorrowingInfos(studentId, loginId);
    }

    public Book getBookById(Long bookId, Long loginId) {

        return libraryService.getBookById(bookId, loginId);
    }

    public UserLoginInfos login(LoginInput loginRequest) {
        return libraryService.login(loginRequest);
    }

    public User getUserInfo(Long userId, Long loginId) {
        return adminService.getUserInfo(Math.toIntExact(userId), Math.toIntExact(loginId));
    }
}