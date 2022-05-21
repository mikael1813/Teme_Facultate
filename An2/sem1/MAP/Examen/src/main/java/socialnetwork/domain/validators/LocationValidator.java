package socialnetwork.domain.validators;

import socialnetwork.domain.Location;
import socialnetwork.domain.Utilizator;

public class LocationValidator implements Validator<Location>{
    @Override
    public void validate(Location entity) throws ValidationException {
        String error = "";
        if(entity.getLocationName()=="")
            error=error+"\\t!!!Numele nu trebuie sa fie vid\\n";

        if(!error.isEmpty())
            throw new ValidationException(error);
    }
}
