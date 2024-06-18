package com.lms.graphql.variables.inputs;

import com.lms.graphql.variables.enums.BookType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddBookInput {
    private String name;
    private String author;
    private BookType type;
    private String location;
}

