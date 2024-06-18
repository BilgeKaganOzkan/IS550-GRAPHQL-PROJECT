package com.lms.graphql.variables.inputs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordInput {
    private int loginID;
    private String oldPassword;
    private String newPassword;
}

