package com.lms.graphql.variables.inputs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateContactInfoInput {
    private int loginID;
    private String newTelNumber;
    private String newLocation;
}
