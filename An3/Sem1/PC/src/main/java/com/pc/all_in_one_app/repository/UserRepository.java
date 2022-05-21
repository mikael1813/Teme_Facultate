package com.pc.all_in_one_app.repository;

import com.pc.all_in_one_app.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

//    @Query("{username:'?0'}")
//    User findItemByName(String username);

    @Query(value="{username:'?0'}")
    List<User> findAll(String username);
}
