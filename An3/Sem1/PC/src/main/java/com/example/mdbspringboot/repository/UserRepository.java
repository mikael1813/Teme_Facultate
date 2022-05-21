package com.example.mdbspringboot.repository;

import com.example.mdbspringboot.domain.Penguin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<Penguin, Integer> {

//    @Query("{username:'?0'}")
//    User findItemByName(String username);

    @Query(value="{id:'?0'}")
    List<Penguin> findAll(int id);
}
