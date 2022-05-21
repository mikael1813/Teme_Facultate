package socialnetwork.domain.validators;

import socialnetwork.domain.MessageFromFile;
import socialnetwork.domain.Prietenie;

import java.util.List;

public class MessageValidator implements Validator<MessageFromFile> {
    @Override
    public void validate(MessageFromFile entity) throws ValidationException {
        String error = "";
        Long f1 = entity.getId(), f3 = entity.getFrom();
        List<Long> f2 = entity.getTo();
        if (f1 < 0)
            error = error + "\t!!!Id-ul trebuie sa fie numar natural >0\n";
        for (Long i : f2) {
            if (i < 0)
                error = error + "\t!!!Id-urile utilizatorilor ce primesc mesajul trebuie sa fie numar natural >0\n";
            if(i == f3){
                error = error + "\t!!!Un utilizator nusi poate trimite singur mesaj\n";
            }
        }
        if (f3 < 0)
            error = error + "\t!!!Id-ul utilizatorului ce trimite mesajul trebuie sa fie un numar natural >0\n";
        if (!error.isEmpty())
            throw new ValidationException(error);
    }
}
