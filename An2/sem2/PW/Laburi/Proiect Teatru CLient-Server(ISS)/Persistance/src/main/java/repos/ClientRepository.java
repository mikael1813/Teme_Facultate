package repos;


import domain.Client;

public interface ClientRepository extends Repository<Integer, Client>{
    Client findByUser(String user);
}
