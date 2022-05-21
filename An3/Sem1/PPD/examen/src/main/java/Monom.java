public class Monom {
    private int exponent;
    private int coeficient;

    public Monom(int exponent, int coeficient) {
        this.exponent = exponent;
        this.coeficient = coeficient;
    }

    public int getExponent() {
        return exponent;
    }

    public void setExponent(int exponent) {
        this.exponent = exponent;
    }

    public int getCoeficient() {
        return coeficient;
    }

    public void setCoeficient(int coeficient) {
        this.coeficient = coeficient;
    }

    @Override
    public String toString() {
        return "Monom{" +
                "exponent=" + exponent +
                ", coeficient=" + coeficient +
                '}';
    }
}
