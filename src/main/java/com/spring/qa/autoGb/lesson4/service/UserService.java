package com.spring.qa.autoGb.lesson4.service;


import com.spring.qa.autoGb.lesson2.example.dto.User;
import org.springframework.web.client.HttpStatusCodeException;

public interface UserService {

    void save(User userDto);

    void update(User userDto);

    User getById(Integer id);

    void delete(Integer id);
}
