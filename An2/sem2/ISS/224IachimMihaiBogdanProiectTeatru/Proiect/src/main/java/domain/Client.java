package domain;

import java.util.List;

public class Client extends Utilizator{
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
