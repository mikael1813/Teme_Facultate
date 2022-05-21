package domain;

import java.io.Serializable;

public class Spectacol extends Entity<Integer> implements Serializable {
    String nume;
    String date;

    public Spectacol(String nume, String date) {
        this.nume = nume;
        this.date = date;
    }

    public Spectacol(){

    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Spectacol{" +
                "nume='" + nume + '\'' +
                ", date=" + date +
                '}';
    }
}

