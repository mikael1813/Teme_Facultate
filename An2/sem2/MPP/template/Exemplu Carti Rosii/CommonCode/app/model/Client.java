package app.model;

import java.io.Serializable;

public class Client extends Utilizator implements Serializable {
    public Client(String user, String parola, String nume) {
        super(user, parola, nume);
    }

    public Client(String user, String parola) {
        super(user, parola);
    }

    public Client() {
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
