package socialnetwork.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utilizator extends Entity<Long> {
    private String firstName;
    private String lastName;
    private List<Utilizator> friends;

    /**
     * Metoda creaza un utilizator avand firstName firstName si lastName lastName
     *
     * @param firstName de tip String
     * @param lastName  de tip String
     */
    public Utilizator(String firstName, String lastName) {
        friends = new ArrayList<>();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * @return firstName-ul utilizatorului
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Metoda seteaza firstName-ul utilizatorului ca fiind firstName
     *
     * @param firstName de tip String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return lastName-ul utilizatorului
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Metoda seteaza lastName-ul utilizatorului ca fiind lastName
     *
     * @param lastName de tip String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return lista de prieteni utilizatori ai acestui utilizator
     */
    public List<Utilizator> getFriends() {
        return friends;
    }

    @Override
    public String toString() {
        String str = "Utilizator{" +
                "ID=" + super.getId() + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", friends=[";
        int nr = 0;
        for (Utilizator u : friends) {
            str = str + u.getId();
            nr++;
            if (nr != friends.size())
                str = str + ",";
        }
        str = str + "]}";
       return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getFriends().equals(that.getFriends());
    }

    /**
     * Metoda adauga in lista de prieteni ai utilizatorului, utilizatorul x, doar daca nu exista deja
     *
     * @param x de tip Utilizator
     */
    public void addFriend(Utilizator x) {
        if (!friends.contains(x))
            friends.add(x);
    }

    /**
     * Metoda elimina utilizatorul x tin lista de prieteni ai utilizatorului, doar daca exista deja
     *
     * @param x de tip Utilizator
     */
    public void deletePrieten(Utilizator x) {
        if (friends.contains(x))
            friends.remove(x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getFriends());
    }
}