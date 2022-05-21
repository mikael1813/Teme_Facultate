package com.example.mdbspringboot.repository;

import org.springframework.stereotype.Component;

@Component
public class CustomUserRepositoryImpl implements CustomUserRepository {
    @Override
    public void updateItemQuantity(String name, float newQuantity) {

    }

//    @Autowired
//    MongoTemplate mongoTemplate;
//
//    public void updateItemQuantity(String name, float newQuantity) {
//        Query query = new Query(Criteria.where("name").is(name));
//        Update update = new Update();
//        update.set("quantity", newQuantity);
//
//        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
//
//        if (result == null)
//            System.out.println("No documents updated");
//        else
//            System.out.println(result.getModifiedCount() + " document(s) updated..");
//
//    }

}
