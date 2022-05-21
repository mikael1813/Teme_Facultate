package com.example.mdbspringboot.rest;

//import SQL.Repository;

import com.example.mdbspringboot.domain.Penguin;
import com.example.mdbspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/penguins")
public class Controller {

    @Autowired
    private UserRepository repo;

    public Controller() {
        //repo = new Repository();
        //System.out.println("dada");
        // repo.findAll();
        //repo.findAll();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getUsers() {
        List<Penguin> list = new ArrayList<>();

        repo.findAll().forEach(user -> {
            list.add(user);
        });

        if (list.isEmpty()) {
            return new ResponseEntity<String>("No users in Database", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<List<Penguin>>(list, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserByUsername(@PathVariable Integer id) {
//        List<Optional<User>> list = new ArrayList<>();
//        list.add(null);
//        repo.findAll().forEach(user -> {
//            if (user.getUsername().equals(username)) {
//                list.remove(0);
//                list.add(user);
//            }
//        });

        Optional<Penguin> user = repo.findById(id);

        if (user.isEmpty()) {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Penguin>(user.get(), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Penguin penguin) {
        //if (repo.findById(penguin.getId()).isEmpty()) {
            repo.save(penguin);
            return new ResponseEntity<Penguin>(penguin, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<String>("Username already used", HttpStatus.BAD_REQUEST);
//        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Penguin penguin) {
        //System.out.println("Updating user ...");

        if (repo.findById(id).isEmpty()) {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        } else {
            repo.deleteById(id);
            repo.save(penguin);
            return new ResponseEntity<Penguin>(penguin, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        //System.out.println("Deleting user ... " + id);

        if (repo.findById(id).isEmpty()) {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        } else {
            repo.deleteById(id);
            return new ResponseEntity<Penguin>(HttpStatus.OK);
        }


    }
}
