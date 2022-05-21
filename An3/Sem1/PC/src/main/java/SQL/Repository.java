//package SQL;
//
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.Filters;
//import com.mongodb.client.result.DeleteResult;
//import com.mongodb.client.result.InsertOneResult;
//import com.mongodb.client.result.UpdateResult;
//import com.example.mdbspringboot.domain.Penguin;
//import org.bson.BsonValue;
//import org.bson.Document;
//import org.bson.conversions.Bson;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.mongodb.client.model.Updates.*;
//
//public class Repository {
//
//    //private MongoClient client;
//    //private MongoDatabase db;
//    private MongoCollection col;
//
//    public Repository() {
//        System.out.println("incerc");
//        System.out.println("incerc");
//        System.out.println("incerc");
//        System.out.println("incerc");
//        System.out.println("incerc");
//        System.out.println("incerc");
//        System.out.println("incerc");
//        System.out.println("incerc");
//        System.out.println("incerc");
//        System.out.println("incerc");
//        System.out.println("incerc");
//        System.out.println("incerc");
//        MongoClient client = MongoClients.create("mongodb+srv://new-user:ZB9R0u2KGpCE3iNu@database.o3gox.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
//        MongoDatabase db = client.getDatabase("HoustonDB");
//
//        col = db.getCollection("domain.User");
//        var s = col.find();
//        System.out.println(col.find());
//        //List<Document> list = (List<Document>) col.find().into(new ArrayList());
//        System.out.println();
//    }
//
//    public InsertOneResult add(Penguin penguin) {
//        // user trebuie sa aibe valori in toate atributele sale
//        Bson filter = Filters.eq("username", penguin.getUsername());
//        Document document1 = (Document) col.find(filter).first();
//
//
//        InsertOneResult result = new InsertOneResult() {
//            @Override
//            public boolean wasAcknowledged() {
//                return false;
//            }
//
//            @Override
//            public BsonValue getInsertedId() {
//                return null;
//            }
//        };
//
//        if (document1 != null) {
//            // acest caz are loc cand username-ul lui user nu este unic
//
//            return result;
//
//        } else {
//            Document document = new Document();
//            document.append("username", penguin.getUsername())
//                    .append("password", penguin.getPassword())
//                    .append("dataNasterii", penguin.getDataNasterii().toString())
//                    .append("firstName", penguin.getFirstName())
//                    .append("lastName", penguin.getLastName())
//                    .append("phone", penguin.getPhone());
//
//            result = col.insertOne(document);
//
//
//            return result;
//        }
//    }
//
//    public DeleteResult delete(String username) {
//        Bson filter = Filters.eq("username", username);
//        DeleteResult result = col.deleteOne(filter);
//
//        return result;
//    }
//
//    public UpdateResult update(String username, Penguin penguin) {
//
//        // update one document
//        Bson filter = Filters.eq("username", username);
//        Bson updateOperation1 = set("username", penguin.getUsername());
//        Bson updateOperation2 = set("password", penguin.getPassword());
//        Bson updateOperation3 = set("dataNasterii", penguin.getDataNasterii().toString());
//        Bson updateOperation4 = set("firstName", penguin.getFirstName());
//        Bson updateOperation5 = set("lastName", penguin.getLastName());
//        Bson updateOperation6 = set("phone", penguin.getPhone());
//        Bson updateOperation = combine(updateOperation1, updateOperation2, updateOperation3, updateOperation4, updateOperation5, updateOperation6);
//
//
//        UpdateResult updateResult = col.updateOne(filter, updateOperation);
//
//        //System.out.println(updateResult);
//        //System.out.println(updateResult.getModifiedCount());
//
//        return updateResult;
//    }
//
//    public List<Penguin> getUsers() {
//        List<Document> list = (List<Document>) col.find().into(new ArrayList());
//
//        List<Penguin> penguins = new ArrayList<>();
//
//        list.forEach(item -> {
//            System.out.println(item.toJson());
//            Penguin penguin = new Penguin(item.getString("username"), item.getString("password"), item.getString("firstName"), item.getString("lastName"), LocalDate.parse(item.getString("dataNasterii")), item.getString("phone"));
//            penguins.add(penguin);
//        });
//
//        return penguins;
//    }
//}
