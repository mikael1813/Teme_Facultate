package domain;

import java.util.List;

public class Rezervare extends Entity<Integer>{
    Client client;
    Loc loc;

    public Rezervare(Client client, Loc loc) {
        this.client = client;
        this.loc = loc;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loc getLoc() {
        return loc;
    }

    public void setLoc(Loc loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "Rezervare{" +
                "client=" + client +
                ", loc=" + loc +
                '}';
    }
}
