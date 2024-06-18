package com.lms.graphql.variables.returns;

import com.lms.graphql.variables.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginInfos {
    private String userId;
    private UserType userType;
}
