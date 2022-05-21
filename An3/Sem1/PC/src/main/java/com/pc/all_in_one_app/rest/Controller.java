package com.pc.all_in_one_app.rest;

import com.pc.all_in_one_app.repository.UserRepository;
import com.pc.all_in_one_app.domain.User;
import com.pc.all_in_one_app.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class Controller {
    //private final UserRepository repo;
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getUsers() {
        return service.findAllUsers();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return service.findUserByUsername(username);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody User user) {
        return service.saveUser(user);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable String username, @RequestBody User user) {
        return service.updateUser(username, user);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String username) {
        return service.deleteUser(username);
    }
}
