package socialnetwork.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageFromFile extends Entity<Long> {
    Long from;
    List<Long> to = new ArrayList<Long>();
    String message;
    LocalDateTime data;

    public MessageFromFile(Long from, String message) {
        this.from = from;
        this.message = message;
        data = LocalDateTime.now();
    }

    public MessageFromFile(Long from, List<Long> to, String message, LocalDateTime data) {
        this.from = from;
        this.to = to;
        this.message = message;
        data = LocalDateTime.now();
        this.data = data;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public List<Long> getTo() {
        return to;
    }

    public void setTo(List<Long> to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

}
