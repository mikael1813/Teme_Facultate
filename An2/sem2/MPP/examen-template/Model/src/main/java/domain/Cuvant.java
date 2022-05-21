package domain;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;


public class Cuvant extends Entity<Integer> implements Serializable {
    private String cuvant;
    private String carac1;
    private String carac2;

    public Cuvant(String cuvant, String carac1, String carac2) {
        this.cuvant = cuvant;
        this.carac1 = carac1;
        this.carac2 = carac2;
    }

    public Cuvant(){

    }

    public String getCuvant() {
        return cuvant;
    }

    public void setCuvant(String cuvant) {
        this.cuvant = cuvant;
    }

    public String getCarac1() {
        return carac1;
    }

    public void setCarac1(String carac1) {
        this.carac1 = carac1;
    }

    public String getCarac2() {
        return carac2;
    }

    public void setCarac2(String carac2) {
        this.carac2 = carac2;
    }

    @Override
    public String toString() {
        return "Cuvant{" +
                "cuvant='" + cuvant + '\'' +
                ", carac1='" + carac1 + '\'' +
                ", carac2='" + carac2 + '\'' +
                '}';
    }
}
