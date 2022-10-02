package com.spring.qa.autoGb.lesson4.mapper;


import com.spring.qa.autoGb.lesson2.example.dto.User;
import com.spring.qa.autoGb.lesson4.entity.UserEntity;

public interface UserMapper {

    UserEntity dtoToEntity(User userDto);

    User entityToDto(UserEntity userEntity);
}
