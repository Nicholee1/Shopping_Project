package org.learning.shopping.service;

import org.learning.shopping.entity.User;
import org.springframework.stereotype.Service;

import java.util.Collection;


public interface UserService {
    User findOne(String email);

    Collection<User> findByRole(String role);

    User save(User user);

    User update(User user);
}
