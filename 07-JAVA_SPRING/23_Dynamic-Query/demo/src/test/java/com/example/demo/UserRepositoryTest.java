package com.example.demo;

import com.example.demo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.repository.UserRepository;

import java.util.List;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user1 = new User("John", 25);
        User user2 = new User("Jane", 30);
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    public void testFindUsersByDynamic() {
        List<User> users = userRepository.findUserByDynamic("John", 25);

        System.out.println("=============================================");
        users.forEach(user -> System.out.println(user.getName() + " - " + user.getAge()));

        System.out.flush();
    }
}
