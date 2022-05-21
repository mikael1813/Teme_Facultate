package domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game extends Entity<Integer> implements Serializable {
    private Utilizator u1;
    private List<Card> cards1 = new ArrayList<>();
    private Utilizator u2;
    private List<Card> cards2 = new ArrayList<>();
    private Utilizator u3;
    private List<Card> cards3 = new ArrayList<>();
    private Map<String, Card> currentRound = new HashMap<>();
    int runde = 0;

    public int getRunde() {
        return runde;
    }

    public void setRunde(int runde) {
        this.runde = runde;
    }

    public Map<String, Card> getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Map<String, Card> currentRound) {
        this.currentRound = currentRound;
    }

    public List<Card> getCards1() {
        return cards1;
    }

    public void setCards1(List<Card> cards1) {
        this.cards1 = cards1;
    }

    public List<Card> getCards2() {
        return cards2;
    }

    public void setCards2(List<Card> cards2) {
        this.cards2 = cards2;
    }

    public List<Card> getCards3() {
        return cards3;
    }

    public void setCards3(List<Card> cards3) {
        this.cards3 = cards3;
    }

    public Utilizator getU1() {
        return u1;
    }

    public void setU1(Utilizator u1) {
        this.u1 = u1;
    }

    public Utilizator getU2() {
        return u2;
    }

    public void setU2(Utilizator u2) {
        this.u2 = u2;
    }

    public Utilizator getU3() {
        return u3;
    }

    public void setU3(Utilizator u3) {
        this.u3 = u3;
    }

    public boolean free() {
        if (u1 == null || u2 == null || u3 == null)
            return true;
        return false;
    }

    public boolean full() {
        if (u1 != null && u2 != null && u3 != null)
            return true;
        return false;
    }

    public void add(Utilizator u) {
        if (u1 == null) {
            u1 = u;
        } else if (u2 == null) {
            u2 = u;
        } else {
            u3 = u;
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "u1=" + u1 +
                ", u2=" + u2 +
                ", u3=" + u3 +
                '}';
    }

    public List<Card> app(List<Card> cards1,List<Card> cards) {
        for(Card card:cards){
            cards1.add(card);
        }
        return cards1;
    }
}
