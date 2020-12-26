package com.example.bookrestservice.service;

import com.example.bookrestservice.model.Book;
import com.example.bookrestservice.model.request.BookRequestDto;
import com.example.bookrestservice.model.response.BookResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final List<Book> bookList = new CopyOnWriteArrayList<>();
    private final AtomicInteger counter = new AtomicInteger();

    // for test purposes
    public BookService() {
        bookList.add(new Book(getId(), "title-1", "qwerty"));
        bookList.add(new Book(getId(), "title-2", "asdfg"));
        bookList.add(new Book(getId(), "title-3", "azxcv"));
        bookList.add(new Book(getId(), "title-4", "poiuy"));
        bookList.add(new Book(getId(), "title-5", "mnbvc"));
    }

    Integer getId() {
        return counter.addAndGet(1);
    }

    Book bookFromDto(BookRequestDto requestDto) {
        return new Book(
                getId(),
                requestDto.getTitle(),
                requestDto.getIsbn()
        );
    }

    BookResponseDto dtoFromBook(Book book) {
        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getCreateTime()
        );
    }

    boolean checkBookExists(Integer id) {
        return Objects.isNull(id) || bookList.size() < id - 1;
    }

    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.ok(bookList.stream().unordered().map(this::dtoFromBook).collect(Collectors.toList()));
    }

    public ResponseEntity<?> getBookById(Integer id) {
        if (checkBookExists(id - 1)) {
            return new ResponseEntity<>(Map.of("errorMessage", "Record not found"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(dtoFromBook(bookList.get(id - 1)));

    }

    public ResponseEntity<?> addBook(BookRequestDto requestDto) {
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
        bookList.add(book);
        return ResponseEntity.ok(Map.of("message", "Successfully added"));
    }

    public ResponseEntity<?> editBook(BookRequestDto requestDto) {
        if (checkBookExists(requestDto.getId())) {
            return new ResponseEntity<>(Map.of("errorMessage", "Record not found"), HttpStatus.NOT_FOUND);
        }
        Book book = bookList.get(requestDto.getId() - 1);
        book.setTitle(StringUtils.isEmpty(requestDto.getTitle()) ? book.getTitle() : requestDto.getTitle());
        book.setIsbn(StringUtils.isEmpty(requestDto.getIsbn()) ? book.getIsbn() : requestDto.getIsbn());
        return ResponseEntity.ok(Map.of("message", "Successfully edited"));
    }

    public ResponseEntity<?> deleteBookById(Integer id) {
        if (checkBookExists(id)) {
            return new ResponseEntity<>(Map.of("errorMessage", "Record not found"), HttpStatus.NOT_FOUND);
        }
        bookList.remove(id - 1);
        return ResponseEntity.ok(Map.of("message", "Successfully deleted"));
    }
}
