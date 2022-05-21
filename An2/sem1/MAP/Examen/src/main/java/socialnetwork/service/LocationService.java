package socialnetwork.service;

import socialnetwork.domain.Location;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository;

public class LocationService {
    private final Repository<Double, Location> repo;


    public LocationService(Repository<Double, Location> repo) {
        this.repo = repo;
    }

    public Iterable<Location> getAll() {
        return repo.findAll();
    }
}
