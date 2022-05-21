package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game extends Entity<Integer> implements Serializable {
    List<Utilizator> users = new ArrayList<>();
    List<Integer> totalPoints = new ArrayList<>();
    List<Integer> lastPoints = new ArrayList<>();
    int round = 0;
    String cuvant;

    public Game(){
        totalPoints.add(0);
        totalPoints.add(0);
        totalPoints.add(0);
        lastPoints.add(0);
        lastPoints.add(0);
        lastPoints.add(0);
    }

    public String getCuvant() {
        return cuvant;
    }

    public void setCuvant(String cuvant) {
        this.cuvant = cuvant;
    }

    public List<Utilizator> getUsers() {
        return users;
    }

    public void setUsers(List<Utilizator> users) {
        this.users = users;
    }

    public List<Integer> getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(List<Integer> totalPoints) {
        this.totalPoints = totalPoints;
    }

    public List<Integer> getLastPoints() {
        return lastPoints;
    }

    public void setLastPoints(List<Integer> lastPoints) {
        this.lastPoints = lastPoints;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public boolean free() {
        if(users.size()<3) return true;
        return false;
    }

    public void add(Utilizator u) {
        users.add(u);
    }

    public boolean full() {
        if(users.size()==3) return true;
        return false;
    }
}
