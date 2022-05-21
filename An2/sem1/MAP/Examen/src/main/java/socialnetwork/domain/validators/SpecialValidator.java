package socialnetwork.domain.validators;

import socialnetwork.domain.Hotel;
import socialnetwork.domain.SpecialOffer;

public class SpecialValidator implements Validator<SpecialOffer>{
    @Override
    public void validate(SpecialOffer entity) throws ValidationException {
        String error="";
        if(entity.getPercents()<0 && entity.getPercents()>100)
            error=error+"\\t!!!Percents trebuie sa fie intre 0 si 100\\n";

        if(!error.isEmpty())
            throw new ValidationException(error);

    }
}
