public class Obiect {
    private int id,pret;

    public Obiect(int id, int pret) {
        this.id = id;
        this.pret = pret;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "id=" + id +
                ", pret=" + pret +
                '}';
    }
}
