package com.lms.graphql.variables.inputs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetUserInfoInput {
    private int loginID;
    private int userID;
}
