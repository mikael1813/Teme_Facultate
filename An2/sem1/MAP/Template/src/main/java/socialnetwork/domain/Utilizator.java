package socialnetwork.domain;

import socialnetwork.domain.enums.Activitate;
import socialnetwork.domain.enums.Pozitie;

public class Utilizator extends Entity<Long> {
    private String firstName;
    private String lastName;
    private Pozitie pozitie;
    private Activitate activitate;

    public Utilizator(String firstName, String lastName, Pozitie pozitie, Activitate activitate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pozitie = pozitie;
        this.activitate = activitate;
    }

    public Activitate getActivitate() {
        return activitate;
    }

    public void setActivitate(Activitate activitate) {
        this.activitate = activitate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Pozitie getPozitie() {
        return pozitie;
    }

    public void setPozitie(Pozitie pozitie) {
        this.pozitie = pozitie;
    }
}
