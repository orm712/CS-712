package com.example.demo.service;

import com.example.demo.repository.UserRepository;import com.example.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers(String name, Integer age){
        return userRepository.findUserByDynamic(name, age);
    }
}
