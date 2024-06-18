package com.lms.graphql.variables.inputs;

import com.lms.graphql.variables.enums.UserType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInput {
    private Integer studentID;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String telNumber;
    private String location;
    private String department;
    private UserType type;
}

