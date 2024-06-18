package com.lms.graphql.variables.returns;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserBorrowingInfos {
    private Book book;
    private String borrowingTime;
    private String dueDate;
    private String returningTime;
    private String paid;
    private int fine;
}
