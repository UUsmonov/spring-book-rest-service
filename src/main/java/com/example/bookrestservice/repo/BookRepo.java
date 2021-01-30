package com.example.bookrestservice.repo;

import com.example.bookrestservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Integer> {
}
