package socialnetwork.repository.file;

import socialnetwork.domain.Location;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;

import java.util.List;

public class LocationFile extends AbstractFileRepository<Double, Location> {

    public LocationFile(String fileName, Validator<Location> validator) {
        super(fileName, validator);
    }

    @Override
    public Location extractEntity(List<String> attributes) {
        Location loc = new Location(attributes.get(1));
        loc.setId(Double.parseDouble(attributes.get(0)));

        return loc;
    }

    @Override
    protected String createEntityAsString(Location entity) {
        return entity.getId() + ";" + entity.getLocationName();
    }
}
