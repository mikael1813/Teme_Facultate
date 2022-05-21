package domain;

public class Card extends Entity<Integer>{
    String culoare;
    int nr;

    public Card(String culoare, int nr) {
        this.culoare = culoare;
        this.nr = nr;
    }

    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    @Override
    public String toString() {
        return "Card{" +
                "culoare='" + culoare + '\'' +
                ", nr=" + nr +
                '}';
    }
}
