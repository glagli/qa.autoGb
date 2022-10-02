package com.spring.qa.autoGb.lesson2.example.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    private int id;
    private String firstName;
    private String secondName;
    private int age;
}
