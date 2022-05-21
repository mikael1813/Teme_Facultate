import java.util.List;

public class Reteta {
    int id, nrMed, pret;
    List<Medicament> medicamente;

    public Reteta(int id, int nrMed, List<Medicament> medicamente) {
        this.id = id;
        this.nrMed = nrMed;
        this.medicamente = medicamente;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNrMed() {
        return nrMed;
    }

    public void setNrMed(int nrMed) {
        this.nrMed = nrMed;
    }

    public List<Medicament> getMedicamente() {
        return medicamente;
    }

    public void setMedicamente(List<Medicament> medicamente) {
        this.medicamente = medicamente;
    }

    @Override
    public String toString() {
        return "Reteta{" +
                "id=" + id +
                ", nrMed=" + nrMed +
                ", medicamente=" + medicamente +
                '}';
    }
}
