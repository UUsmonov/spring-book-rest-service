package com.example.bookrestservice.model.response;


import com.example.bookrestservice.model.request.BookRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookResponseDto extends BookRequestDto {
    private Long createTime;

    public BookResponseDto(Integer id,
                           String title,
                           String isbn,
                           Long createTime) {
        super(id, title, isbn);
        this.createTime = createTime;
    }
}
