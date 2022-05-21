package socialnetwork.repository.file;

import socialnetwork.domain.Hotel;
import socialnetwork.domain.Location;
import socialnetwork.domain.enums.Type;
import socialnetwork.domain.validators.Validator;

import java.net.Proxy;
import java.util.List;

public class HotelFile extends AbstractFileRepository<Double, Hotel> {
    public HotelFile(String fileName, Validator<Hotel> validator) {
        super(fileName, validator);
    }

    @Override
    public Hotel extractEntity(List<String> attributes) {
        Hotel hotel = new Hotel(Double.parseDouble(attributes.get(1)),attributes.get(2),Integer.parseInt(attributes.get(3)),Double.parseDouble(attributes.get(4)), Type.valueOf(attributes.get(5)));
        hotel.setId(Double.parseDouble(attributes.get(0)));

        return hotel;
    }

    @Override
    protected String createEntityAsString(Hotel entity) {
        return entity.getId()+";"+entity.getLocationId()+";"+entity.getHotelName()+";"+entity.getNoRooms()+";"+entity.getPricePerNight()+";"+entity.getType();
    }
}
