package socialnetwork.repository.file;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.validators.Validator;

import java.time.LocalDateTime;
import java.util.List;

import static socialnetwork.domain.Constants.DATE_TIME_FORMATTER;

/**
 * Clasa PrietenieFile implementeaza un repository ce mentine datele persistente
 */
public class PrietenieFile extends AbstractFileRepository<Tuple<Long, Long>, Prietenie> {
    public PrietenieFile(String fileName, Validator<Prietenie> validator) {
        super(fileName, validator);
    }

    @Override
    public Prietenie extractEntity(List<String> attributes) {
        return new Prietenie(Long.parseLong(attributes.get(0)), Long.parseLong(attributes.get(1)),
                LocalDateTime.parse(attributes.get(2), DATE_TIME_FORMATTER));
    }

    @Override
    protected String createEntityAsString(Prietenie entity) {
        return entity.getId().getLeft() + ";" + entity.getId().getRight() + ";" + entity.getDate().format(DATE_TIME_FORMATTER);
    }


}
