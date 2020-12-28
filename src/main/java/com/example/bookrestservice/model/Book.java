package com.example.bookrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Book {
    @Id
    @GeneratedValue
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
