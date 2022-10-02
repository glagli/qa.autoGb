package com.spring.qa.autoGb.lesson4.repository;


import com.spring.qa.autoGb.lesson4.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByFirstNameAndSecondName(String firstName, String secondName);

    Optional<UserEntity> findByAddressEntityCity(String city);


}
