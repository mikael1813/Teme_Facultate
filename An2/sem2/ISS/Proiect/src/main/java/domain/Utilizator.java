package domain;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Utilizator extends Entity<Integer>{
    private String user;
    private String parola;
    private String nume;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Utilizator(String user, String parola, String nume) {
        this.user = user;
        this.parola = parola;
        this.nume = nume;
    }

    public Utilizator(String user, String parola) {
        this.user = user;
        this.parola = parola;
    }

    public Utilizator() {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "user='" + user + '\'' +
                ", parola='" + parola + '\'' +
                ", nume='" + nume + '\'' +
                '}';
    }
}
