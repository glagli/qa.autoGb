package com.spring.qa.autoGb.lesson2.example.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    private int id;
    private String bookName;
    private String author;
    private int countOfPages;
}
