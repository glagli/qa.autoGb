package com.spring.qa.autoGb.lesson4.service;

import com.spring.qa.autoGb.lesson2.example.dto.User;
import com.spring.qa.autoGb.lesson4.entity.UserEntity;
import com.spring.qa.autoGb.lesson4.mapper.UserMapper;
import com.spring.qa.autoGb.lesson4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void save(User userDto) {
        UserEntity entity = userMapper.dtoToEntity(userDto);
        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void update(User userDto) {
        UserEntity entity = userRepository.findById(userDto.getId())
                .orElseThrow();
        entity.setFirstName(userDto.getFirstName());
        entity.setSecondName(userDto.getSecondName());
        entity.setAge(userDto.getAge());
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(Integer id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow();
        return userMapper.entityToDto(entity);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
