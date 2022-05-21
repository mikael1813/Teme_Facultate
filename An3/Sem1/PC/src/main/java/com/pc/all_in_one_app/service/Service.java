package com.pc.all_in_one_app.service;


import com.pc.all_in_one_app.domain.User;
import com.pc.all_in_one_app.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Component
public class Service {
    private final UserRepository userRepository;

    public Service(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> saveUser(User user) {
        if (userRepository.findById(user.getUsername()).isEmpty()) {
            userRepository.save(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Username already used", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateUser(String username, User user) {
        if (userRepository.findById(username).isEmpty()) {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        } else {
            userRepository.deleteById(username);
            userRepository.save(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
    }


    public ResponseEntity<?> deleteUser(String username) {
        if (userRepository.findById(username).isEmpty()) {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        } else {
            userRepository.deleteById(username);
            return new ResponseEntity<User>(HttpStatus.OK);
        }
    }

    public ResponseEntity<?> findUserByUsername(String username) {
        Optional<User> user = userRepository.findById(username);

        if (user.isEmpty()) {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<User>(user.get(), HttpStatus.OK);
        }

    }

    public ResponseEntity<?> findAllUsers() {
        List<User> list = userRepository.findAll();
        if (list.isEmpty()) {
            return new ResponseEntity<String>("No users in Database", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<List<User>>(list, HttpStatus.OK);
        }
    }
}
