package socialnetwork.service;

import socialnetwork.domain.Hotel;
import socialnetwork.domain.Location;
import socialnetwork.repository.Repository;

public class HotelService {
    private final Repository<Double, Hotel> repo;


    public HotelService(Repository<Double, Hotel> repo) {
        this.repo = repo;
    }

    public Iterable<Hotel> getAll() {
        return repo.findAll();
    }

}
