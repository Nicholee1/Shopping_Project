package org.learning.shopping.repository;

import org.junit.jupiter.api.Test;
import org.learning.shopping.Repository.UserRepository;
import org.learning.shopping.entity.User;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testUser {
    @Autowired
    UserRepository userRepository;
    @Test
    void testByEmail(){
       User user= userRepository.findByEmail("customer1@email.com");
        System.out.println("-------------------------");
        System.out.println(user.toString());
        System.out.println("-------------------------");
    }
}
