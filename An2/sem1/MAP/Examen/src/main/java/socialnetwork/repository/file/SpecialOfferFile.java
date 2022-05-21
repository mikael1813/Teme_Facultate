package socialnetwork.repository.file;

import socialnetwork.domain.Hotel;
import socialnetwork.domain.SpecialOffer;
import socialnetwork.domain.enums.Type;
import socialnetwork.domain.validators.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static socialnetwork.utils.Constants.DATE_TIME_FORMATTER;

public class SpecialOfferFile extends AbstractFileRepository<Double, SpecialOffer> {
    public SpecialOfferFile(String fileName, Validator<SpecialOffer> validator) {
        super(fileName, validator);
    }

    @Override
    public SpecialOffer extractEntity(List<String> attributes) {
        SpecialOffer offer = new SpecialOffer(Double.parseDouble(attributes.get(1)), LocalDate.parse(attributes.get(2), DATE_TIME_FORMATTER),LocalDate.parse(attributes.get(3), DATE_TIME_FORMATTER),Integer.parseInt(attributes.get(4)));

        offer.setId(Double.parseDouble(attributes.get(0)));

        return offer;
    }

    @Override
    protected String createEntityAsString(SpecialOffer entity) {
        return entity.getId()+";"+entity.getHotelId()+";"+entity.getStartDate()+";"+entity.getEndDate()+";"+ entity.getPercents();
    }
}
