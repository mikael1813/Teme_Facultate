package repos;

import domain.Administrator;

public interface AdministratorRepository extends Repository<Integer, Administrator>{
    Administrator findByUser(String user);
}
