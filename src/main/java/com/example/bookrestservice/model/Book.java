package com.example.bookrestservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    private Integer id;
    private String title;
    private String isbn;
    private Long createTime = System.currentTimeMillis();

    public Book(Integer id,
                String title,
                String isbn) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
    }
}
