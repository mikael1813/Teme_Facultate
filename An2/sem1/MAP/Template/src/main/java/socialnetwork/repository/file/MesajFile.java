package socialnetwork.repository.file;

import socialnetwork.domain.Mesaj;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.enums.Privatitate;
import socialnetwork.domain.validators.Validator;

import java.util.List;

public class MesajFile extends AbstractFileRepository<Long, Mesaj> {
    public MesajFile(String fileName, Validator<Mesaj> validator) {
        super(fileName, validator);
    }

    @Override
    public Mesaj extractEntity(List<String> attributes) {
        //TODO: implement method
        Mesaj msj = new Mesaj(Long.parseLong(attributes.get(1)), Privatitate.valueOf(attributes.get(2)), Long.parseLong(attributes.get(3)), attributes.get(4));
        msj.setId(Long.parseLong(attributes.get(0)));

        return msj;
    }

    @Override
    protected String createEntityAsString(Mesaj entity) {
        return entity.getId() + ";" + entity.getU1() + ";" + entity.getPrivacy().toString() + ";" + entity.getU2() + ";" + entity.getMesaj();
    }
}
