package repos;

import domain.Utilizator;


public interface UtilizatorRepository extends Repository<Integer, Utilizator>{
    public Utilizator findByUser(String username);
}
