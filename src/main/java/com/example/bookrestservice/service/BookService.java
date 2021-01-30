package com.example.bookrestservice.service;

import com.example.bookrestservice.model.BookDto;
import com.example.bookrestservice.model.Book;
import com.example.bookrestservice.repo.BookRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    // for test purposes
    @PostConstruct
    public void init() {
        if (bookRepo.findAll().isEmpty()) {
            List<Book> bookList = new ArrayList<>();
            bookList.add(new Book("book1", UUID.randomUUID().toString()));
            bookList.add(new Book("book2", UUID.randomUUID().toString()));
            bookList.add(new Book("book3", UUID.randomUUID().toString()));
            bookList.add(new Book("book4", UUID.randomUUID().toString()));
            bookList.add(new Book("book5", UUID.randomUUID().toString()));
            bookList.add(new Book("book6", UUID.randomUUID().toString()));

            bookRepo.saveAll(bookList);
        }
    }

    Book bookFromDto(BookDto requestDto) {
        return new Book(
                requestDto.getTitle(),
                requestDto.getIsbn()
        );
    }

    BookDto dtoFromBook(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getCreateTime()
        );
    }

    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.ok(bookRepo.findAll().stream().unordered().map(this::dtoFromBook).collect(Collectors.toList()));
    }

    public ResponseEntity<BookDto> getBookById(Integer id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("record not found");
        }
        Optional<Book> bookOptional = bookRepo.findById(id);
        if (bookOptional.isEmpty()) {
            throw new IllegalArgumentException("record not found");
        }
        return ResponseEntity.ok(dtoFromBook(bookOptional.get()));
    }

    public ResponseEntity<?> addBook(BookDto requestDto) {
        List<String> errorMessages = new ArrayList<>();
        boolean isSuccess = true;
        if (StringUtils.isEmpty(requestDto.getTitle())) {
            errorMessages.add("Title is empty");
            isSuccess = false;
//            return new ResponseEntity<>(Map.of("errorMessage", "Title is empty"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isEmpty(requestDto.getIsbn())) {
            errorMessages.add("ISBN is empty");
            isSuccess = false;
//            return new ResponseEntity<>(Map.of("errorMessage", "ISBN is empty"), HttpStatus.BAD_REQUEST);
        }
        if (!isSuccess) {
            return new ResponseEntity<>(Map.of("errorMessages", errorMessages), HttpStatus.BAD_REQUEST);
        }
        Book book = bookFromDto(requestDto);
        bookRepo.save(book);
        return ResponseEntity.ok(Map.of("message", "Successfully added"));
    }

    public ResponseEntity<?> editBook(BookDto requestDto) {
        if (Objects.isNull(requestDto.getId())) {
            return new ResponseEntity<>(Map.of("errorMessage", "Record not found"), HttpStatus.NOT_FOUND);
        }
        Optional<Book> bookOptional = bookRepo.findById(requestDto.getId());
        if (bookOptional.isEmpty()) {
            return new ResponseEntity<>(Map.of("errorMessage", "Record not found"), HttpStatus.NOT_FOUND);
        }
        Book book = bookOptional.get();
        book.setTitle(StringUtils.isEmpty(requestDto.getTitle()) ? book.getTitle() : requestDto.getTitle());
        book.setIsbn(StringUtils.isEmpty(requestDto.getIsbn()) ? book.getIsbn() : requestDto.getIsbn());
        bookRepo.save(book);
        return ResponseEntity.ok(Map.of("message", "Successfully edited"));
    }

    public ResponseEntity<?> deleteBookById(Integer id) {
        if (Objects.isNull(id)) {
            return new ResponseEntity<>(Map.of("errorMessage", "Record not found"), HttpStatus.NOT_FOUND);
        }
        bookRepo.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Successfully deleted"));
    }
}
