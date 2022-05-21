package domain;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class Entity<ID> implements Serializable {
    private ID id;
    public ID getId() {
        return id;
    }
    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "domain.Entity{" +
                "id=" + id +
                '}';
    }
}
