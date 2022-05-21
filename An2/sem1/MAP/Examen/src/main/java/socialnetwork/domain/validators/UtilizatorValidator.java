package socialnetwork.domain.validators;

import socialnetwork.domain.Utilizator;

/**
 * Relizeaza validarile entitatilor de tip utilizator
 */
public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException  {
        String error = "";
        if (entity.getFirstName().isEmpty())
            error = error + "\t!!!Numele nu trebuie sa fie vid\n";
        if (entity.getLastName().isEmpty())
            error = error + "\t!!!Prenumele nu trebuie sa fie vid\n";
        if(entity.getFirstName().matches(".*\\d.*"))
            error = error + "\t!!!Numele nu poate contine cifre\n";
        if(entity.getLastName().matches(".*\\d.*"))
            error = error + "\t!!!Prenumele nu poate contine cifre\n";
        if (!error.isEmpty())
            throw new ValidationException(error);
    }
}
