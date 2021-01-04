package com.example.bookrestservice.controller;

import com.example.bookrestservice.model.request.BookRequestDto;
import com.example.bookrestservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable("id") Integer id) {
        return bookService.getBookById(id);
    }

    @PostMapping()
    public ResponseEntity<?> addBook(@RequestBody BookRequestDto requestDto) {
        return bookService.addBook(requestDto);
    }

    @PutMapping
    public ResponseEntity<?> editBook(@RequestBody BookRequestDto requestDto) {
        return bookService.editBook(requestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable("id") Integer id) {
        return bookService.deleteBookById(id);
    }
}
