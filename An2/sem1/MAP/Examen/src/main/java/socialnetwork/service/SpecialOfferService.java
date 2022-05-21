package socialnetwork.service;

import socialnetwork.domain.Location;
import socialnetwork.domain.SpecialOffer;
import socialnetwork.repository.Repository;

public class SpecialOfferService {
    private final Repository<Double, SpecialOffer> repo;


    public SpecialOfferService(Repository<Double, SpecialOffer> repo) {
        this.repo = repo;
    }

    public Iterable<SpecialOffer> getAll() {
        return repo.findAll();
    }
}
