package com.lms.graphql.variables.inputs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserInfoInput {
    private int loginID;
    private int userID;
    private UserInput user;
}

