package domain;


import domain.enums.Culoare;

import java.io.Serializable;


public class Carte extends Entity<Integer> implements Serializable{
    Culoare culoare;
    int numar;

    public Carte(Culoare culoare, int numar) {
        this.culoare = culoare;
        this.numar = numar;
    }

    public Culoare getCuloare() {
        return culoare;
    }

    public void setCuloare(Culoare culoare) {
        this.culoare = culoare;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    @Override
    public String toString() {
        return "Carte{" +
                "culoare=" + culoare +
                ", numar=" + numar +
                '}';
    }
}
