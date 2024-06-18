package com.lms.graphql.variables.inputs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReturnBookInput {
    private String studentId;
    private String bookId;
    private String returningTime;
}
