import java.time.LocalDateTime;
import java.util.List;

public class Valoare {
    Operatie operatie;
    int nr_obiecte;
    LocalDateTime time;

    public Valoare(Operatie operatie, int nr_obiecte, LocalDateTime time) {
        this.operatie = operatie;
        this.nr_obiecte = nr_obiecte;
        this.time = time;
    }

    public Operatie getOperatie() {
        return operatie;
    }

    public void setOperatie(Operatie operatie) {
        this.operatie = operatie;
    }

    public int getNr_obiecte() {
        return nr_obiecte;
    }

    public void setNr_obiecte(int nr_obiecte) {
        this.nr_obiecte = nr_obiecte;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Valoare{" +
                "operatie=" + operatie +
                ", nr_obiecte=" + nr_obiecte +
                ", time=" + time +
                '}';
    }
}
