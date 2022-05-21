package socialnetwork.domain;

import java.time.LocalDateTime;
import java.util.List;

public class ReplyMessage extends Message{
    Message m;

    public ReplyMessage(Utilizator from, List<Utilizator> to, String message, LocalDateTime data) {
        this.from = from;
        this.to = to;
        this.message = message;
        data = LocalDateTime.now();
        this.data = data;
    }
}
