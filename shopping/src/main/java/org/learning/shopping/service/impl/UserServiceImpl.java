package org.learning.shopping.service.impl;

import org.learning.shopping.Repository.CartRepository;
import org.learning.shopping.Repository.UserRepository;
import org.learning.shopping.entity.Cart;
import org.learning.shopping.entity.User;
import org.learning.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@DependsOn("passwordEncoder")
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Override
    public User findOne(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Collection<User> findByRole(String role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    @Transactional
    public User save(User user) {  //save user, change pwd to encode pwd, initial cart for user
        user.setPassword(passwordEncoder.encode(user.getPassword())); // password encode
        try{
            User savedUser = userRepository.save(user);

            // initial Cart
            Cart savedCart = cartRepository.save(new Cart(savedUser));
            savedUser.setCart(savedCart);
            return userRepository.save(savedUser); //jpa 的save会先query再save，所以只有真的更新，才会save，因此不会save两条

        }catch (Exception e){
            throw e;
        }

    }

    @Override
    @Transactional
    public User update(User user) {
       User oldUser=userRepository.findByEmail(user.getEmail());
       oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
       oldUser.setName(user.getName());
       oldUser.setPhone(user.getPhone());
       oldUser.setAddress(user.getAddress());
       return userRepository.save(oldUser);
    }
}
