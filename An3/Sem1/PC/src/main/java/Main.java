import com.mongodb.client.*;

import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Main {

    public static void main(String[] args) {
//        MongoClient client = MongoClients.create("mongodb+srv://new-user:ZB9R0u2KGpCE3iNu@database.o3gox.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
//
//        MongoDatabase db = client.getDatabase("HoustonDB");
//
//        MongoCollection col = db.getCollection("domain.User");
//
//        Document sampleDoc = new Document("_id",Integer.parseInt("1")).append("name","Ion Preafericitul");
//
//        //col.insertOne(sampleDoc);
//
//        List<Document> list = (List<Document>) col.find().into(new ArrayList());
//
//        list.forEach(item->{
//            System.out.println(item.toJson());
//            System.out.println(item.get("name"));
//        });


        //SpringApplication.run(Main.class, args);

//        repository.Repository r = new repository.Repository();
//        LocalDate date = LocalDate.now();
//        //r.getUsers();
//        domain.User u = new domain.User("usernameee", "password", "Ion", "Ion", date, "077777777");
//
//        //r.add(u);
//        System.out.println(r.getUsers());
//        //r.delete("usernameee");
//        //r.update("usernamee", u);

    }
}
