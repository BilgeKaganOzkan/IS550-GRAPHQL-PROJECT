package com.lms.graphql.variables.returns;

import com.lms.graphql.variables.enums.BookType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Book {
    private String id;
    private String name;
    private String author;
    private BookType type;
    private String location;
    private String available;

}