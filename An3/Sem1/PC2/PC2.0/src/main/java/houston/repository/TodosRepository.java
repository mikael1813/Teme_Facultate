package houston.repository;


import houston.entity.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TodosRepository extends MongoRepository<Todo, String> {

}
