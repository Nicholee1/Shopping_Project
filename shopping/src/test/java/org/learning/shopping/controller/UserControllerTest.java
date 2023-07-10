package org.learning.shopping.controller;

import org.junit.jupiter.api.Test;
import org.learning.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    UserService userService;

    @Test
    void testFindByEmail(){
        var email="customer1@email.com";
        System.out.println("-------------");
        System.out.println(userService.findOne(email));
        System.out.println("-------------");
    }

}
