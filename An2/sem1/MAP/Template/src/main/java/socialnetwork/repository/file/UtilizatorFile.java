package socialnetwork.repository.file;

import socialnetwork.domain.Utilizator;
import socialnetwork.domain.enums.Activitate;
import socialnetwork.domain.enums.Pozitie;
import socialnetwork.domain.validators.Validator;

import java.util.List;

public class UtilizatorFile extends AbstractFileRepository<Long, Utilizator>{

    public UtilizatorFile(String fileName, Validator<Utilizator> validator) {
        super(fileName, validator);
    }

    @Override
    public Utilizator extractEntity(List<String> attributes) {
        //TODO: implement method
        Utilizator user = new Utilizator(attributes.get(1),attributes.get(2), Pozitie.valueOf(attributes.get(3)), Activitate.valueOf(attributes.get(4)));
        user.setId(Long.parseLong(attributes.get(0)));

        return user;
    }

    @Override
    protected String createEntityAsString(Utilizator entity) {
        return entity.getId()+";"+entity.getFirstName()+";"+entity.getLastName()+";"+entity.getPozitie()+";"+entity.getActivitate();
    }
}
