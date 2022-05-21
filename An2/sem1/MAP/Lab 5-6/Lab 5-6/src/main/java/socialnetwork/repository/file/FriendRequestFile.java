package socialnetwork.repository.file;

import socialnetwork.domain.FriendRequest;
import socialnetwork.domain.Enums.Stare;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.validators.Validator;

import java.time.LocalDateTime;
import java.util.List;

import static socialnetwork.domain.Constants.DATE_TIME_FORMATTER;

public class FriendRequestFile extends AbstractFileRepository<Tuple<Tuple<Long, Long>,LocalDateTime>, FriendRequest> {
    public FriendRequestFile(String fileName, Validator<FriendRequest> validator) {
        super(fileName, validator);
    }

    @Override
    public FriendRequest extractEntity(List<String> attributes) {
        System.out.println(attributes.get(2));
        return new FriendRequest(Long.parseLong(attributes.get(0)), Long.parseLong(attributes.get(1)),
                LocalDateTime.parse(attributes.get(2), DATE_TIME_FORMATTER), Stare.valueOf(attributes.get(3)));
    }

    @Override
    protected String createEntityAsString(FriendRequest entity) {
        return entity.getId().getLeft().getLeft() + ";" + entity.getId().getLeft().getRight()+ ";" + entity.getId().getRight().format(DATE_TIME_FORMATTER)+";"+entity.getState();
    }
}
