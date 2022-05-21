package socialnetwork.repository.file;

import socialnetwork.domain.Message;
import socialnetwork.domain.MessageFromFile;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static socialnetwork.domain.Constants.DATE_TIME_FORMATTER;

public class MessageFile extends AbstractFileRepository<Long, MessageFromFile> {

    public MessageFile(String fileName, Validator<MessageFromFile> validator) {
        super(fileName, validator);
    }

    @Override
    public MessageFromFile extractEntity(List<String> attributes) {
        Long id = Long.parseLong(attributes.get(0));
        Long to = Long.parseLong(attributes.get(1));

        int size = Integer.parseInt(attributes.get(2));
        List<Long> list = new ArrayList<Long>();

        for(int i=0;i<size;i++){
            list.add(Long.parseLong(attributes.get(3+i)));
        }

        String message = attributes.get(4+size-1);
        LocalDateTime date = LocalDateTime.parse(attributes.get(5+size-1),DATE_TIME_FORMATTER);

        MessageFromFile m  = new MessageFromFile(to,list,message,date);
        m.setId(id);
        return m;
    }

    @Override
    protected String createEntityAsString(MessageFromFile entity) {
        String s = "";
        int n = entity.getTo().size();

        for (Long u : entity.getTo()) {
            s = s + u + ";";
        }

        return entity.getId() + ";" + entity.getFrom() + ";" + n + ";" + s + entity.getMessage() + ";" + entity.getData().format(DATE_TIME_FORMATTER);

    }
}
