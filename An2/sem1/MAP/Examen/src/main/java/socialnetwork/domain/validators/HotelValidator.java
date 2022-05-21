package socialnetwork.domain.validators;

import socialnetwork.domain.Hotel;
import socialnetwork.domain.Utilizator;

public class HotelValidator implements Validator<Hotel>{
    @Override
    public void validate(Hotel entity) throws ValidationException {
        String error = "";
        if(entity.getHotelName()=="")
            error=error+"\\t!!!Numele nu trebuie sa fie vid\\n";

        if(!error.isEmpty())
            throw new ValidationException(error);
    }
}
