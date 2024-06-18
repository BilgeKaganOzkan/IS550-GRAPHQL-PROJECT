package com.lms.graphql.variables.inputs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BorrowBookInput {
    private String studentId;
    private String bookId;
    private String borrowingTime;
    private String dueDate;
}
