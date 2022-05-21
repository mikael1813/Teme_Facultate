package socialnetwork.domain.validators;

import socialnetwork.domain.FriendRequest;
import socialnetwork.domain.MessageFromFile;

public class FriendRequestValidator implements Validator<FriendRequest>{

    @Override
    public void validate(FriendRequest entity) throws ValidationException {
        String error = "";
        Long f1 = entity.getId().getLeft().getLeft(), f2 = entity.getId().getLeft().getRight();
        if (f1 < 0)
            error = error + "\t!!!Primul utilizator trebuie sa aiba un id numar natural >0\n";
        if (f2 < 0)
            error = error + "\t!!!Al doilea utilizator trebuie sa aiba un id numar natural >0\n";
        if (error.isEmpty() && f1 == f2)
            error = error + "\t!!!Un utilizator nu poate fi prieten cu el insusi\n";
        if (!error.isEmpty())
            throw new ValidationException(error);
    }
}
