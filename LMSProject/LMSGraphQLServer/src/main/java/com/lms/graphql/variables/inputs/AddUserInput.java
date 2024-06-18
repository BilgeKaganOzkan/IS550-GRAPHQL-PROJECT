package com.lms.graphql.variables.inputs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddUserInput {
    private int loginID;
    private UserInput user;
}

