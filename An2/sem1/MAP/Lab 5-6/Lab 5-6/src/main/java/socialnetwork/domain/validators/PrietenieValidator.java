package socialnetwork.domain.validators;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Utilizator;

/**
 * Relizeaza validarile entitatilor de tip prietenie
 */
public class PrietenieValidator implements Validator<Prietenie> {
    @Override
    public void validate(Prietenie entity) throws ValidationException {
        String error = "";
        Long f1 = entity.getId().getLeft(), f2 = entity.getId().getRight();
        if (f1 < 0)
            error = error + "\t!!!Primul prieten trebuie sa aiba un id numar natural >0\n";
        if (f2 < 0)
            error = error + "\t!!!Al doilea prieten trebuie sa aiba un id numar natural >0\n";
        if (error.isEmpty() && f1 == f2)
            error = error + "\t!!!Un utilizator nu poate fi prieten cu el insusi\n";
        if (!error.isEmpty())
            throw new ValidationException(error);
    }
}