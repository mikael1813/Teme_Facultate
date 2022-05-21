package domain;

import domain.Entity;

import java.io.Serializable;

public class Utilizator extends Entity<Integer> implements Serializable {
    private String user;
    private String parola;

    public Utilizator(String user, String parola) {
        this.user = user;
        this.parola = parola;
    }
    public Utilizator(){

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
}
