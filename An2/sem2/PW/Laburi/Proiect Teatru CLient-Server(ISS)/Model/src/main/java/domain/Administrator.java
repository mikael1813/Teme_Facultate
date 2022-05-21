package domain;

import java.io.Serializable;

public class Administrator extends Utilizator implements Serializable {
    public Administrator(String user, String parola, String nume) {
        super(user, parola, nume);
    }

    public Administrator(String user, String parola) {
        super(user, parola);
    }

    public Administrator() {
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
