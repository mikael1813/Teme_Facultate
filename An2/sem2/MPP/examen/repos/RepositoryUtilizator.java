package repos;

import domain.Utilizator;

public interface RepositoryUtilizator extends Repository<Integer, Utilizator>{
    Utilizator findBy(String username,String parola);
}
