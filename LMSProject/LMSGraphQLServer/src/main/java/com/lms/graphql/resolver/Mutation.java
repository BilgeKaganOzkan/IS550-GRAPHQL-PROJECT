package com.lms.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.lms.graphql.service.GrpcClientService;
import com.lms.graphql.service.RestClientService;
import com.lms.graphql.variables.inputs.*;
import com.lms.graphql.variables.returns.GenericResponse;
import com.lms.graphql.variables.returns.ReturnType;
import com.lms.graphql.variables.returns.UserID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mutation implements GraphQLMutationResolver {

    @Autowired
    private RestClientService libraryService;

    @Autowired
    private GrpcClientService adminService;

    public ReturnType addBorrowBook(BorrowBookInput borrowBook, Long loginId) {
        return libraryService.addBorrowBook(borrowBook, loginId);
    }

    public ReturnType addReturnBook(ReturnBookInput returnBook, Long loginId) {
        return libraryService.addReturnBook(returnBook, loginId);
    }

    public ReturnType addBook(AddBookInput addBook, Long loginId) {
        return libraryService.addBook(addBook, loginId);
    }

    public ReturnType deleteBook(Long bookId, Long loginId) {
        return libraryService.deleteBook(bookId, loginId);
    }

    public UserID addUser(AddUserInput input) {
        return adminService.addUser(input);
    }

    public GenericResponse deleteUser(DeleteUserInput input) {
        return adminService.deleteUser(input);
    }

    public GenericResponse changePassword(ChangePasswordInput input) {
        return adminService.changePassword(input);
    }

    public GenericResponse updateContactInfo(UpdateContactInfoInput input) {
        return adminService.updateContactInfo(input);
    }

    public GenericResponse updateUserInfo(UpdateUserInfoInput input) {
        return adminService.updateUserInfo(input);
    }
}