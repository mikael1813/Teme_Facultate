package app.model;

import app.model.enums.Stare;


public class Loc extends Entity<Integer> {
    int loja,numar,rand;
    float pret;
    Stare stare;

    public Stare getStare() {
        return stare;
    }

    public void setStare(Stare stare) {
        this.stare = stare;
    }

    public Loc(int loja, int numar, int rand, float pret, Stare stare) {
        this.loja = loja;
        this.numar = numar;
        this.rand = rand;
        this.pret = pret;
        this.stare = stare;
    }

    public Loc(){

    }

    public int getLoja() {
        return loja;
    }

    public void setLoja(int loja) {
        this.loja = loja;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public int getRand() {
        return rand;
    }

    public void setRand(int rand) {
        this.rand = rand;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }


    @Override
    public String toString() {
        return "Loc{" +
                "loja=" + loja +
                ", numar=" + numar +
                ", rand=" + rand +
                ", pret=" + pret +
                ", stare=" + stare +
                '}';
    }
}


