package com.example.bookrestservice.controller;

import com.example.bookrestservice.model.response.BookResponseDto;
import com.example.bookrestservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void getBookById() throws Exception {

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authentication", String.format("Basic %s",  Base64.getEncoder().encodeToString("az0usmlgbt93fhx27:33a481be-0cb4-4cc9-a73f-310311d9b24a".getBytes())));
// prepare

        BookResponseDto bookResponseDto = new BookResponseDto(1, "abc", "isbn", System.currentTimeMillis());
        Mockito.when(bookService.getBookById(1)).thenReturn(ResponseEntity.ok(bookResponseDto));

        // act
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/book/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2));

    }
}