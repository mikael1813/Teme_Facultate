package domain;

import java.io.Serializable;

public class Round extends Entity<Integer> implements Serializable {
    String model1;
    String model2;
    String model3;
    String propunere1;
    String propunere2;
    String propunere3;
    int puncte1;
    int puncte2;
    int puncte3;
    int game;

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public Round() {

    }

    public Round(String model1, String model2, String model3, String propunere1, String propunere2, String propunere3, int puncte1, int puncte2, int puncte3) {
        this.model1 = model1;
        this.model2 = model2;
        this.model3 = model3;
        this.propunere1 = propunere1;
        this.propunere2 = propunere2;
        this.propunere3 = propunere3;
        this.puncte1 = puncte1;
        this.puncte2 = puncte2;
        this.puncte3 = puncte3;
    }

    public String getModel1() {
        return model1;
    }

    public void setModel1(String model1) {
        this.model1 = model1;
    }

    public String getModel2() {
        return model2;
    }

    public void setModel2(String model2) {
        this.model2 = model2;
    }

    public String getModel3() {
        return model3;
    }

    public void setModel3(String model3) {
        this.model3 = model3;
    }

    public String getPropunere1() {
        return propunere1;
    }

    public void setPropunere1(String propunere1) {
        this.propunere1 = propunere1;
    }

    public String getPropunere2() {
        return propunere2;
    }

    public void setPropunere2(String propunere2) {
        this.propunere2 = propunere2;
    }

    public String getPropunere3() {
        return propunere3;
    }

    public void setPropunere3(String propunere3) {
        this.propunere3 = propunere3;
    }

    public int getPuncte1() {
        return puncte1;
    }

    public void setPuncte1(int puncte1) {
        this.puncte1 = puncte1;
    }

    public int getPuncte2() {
        return puncte2;
    }

    public void setPuncte2(int puncte2) {
        this.puncte2 = puncte2;
    }

    public int getPuncte3() {
        return puncte3;
    }

    public void setPuncte3(int puncte3) {
        this.puncte3 = puncte3;
    }

    public boolean full() {
        if (propunere1 != null && propunere2 != null && propunere3 != null)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "Round{" +
                "model1='" + model1 + '\'' +
                ", model2='" + model2 + '\'' +
                ", model3='" + model3 + '\'' +
                ", propunere1='" + propunere1 + '\'' +
                ", propunere2='" + propunere2 + '\'' +
                ", propunere3='" + propunere3 + '\'' +
                ", puncte1=" + puncte1 +
                ", puncte2=" + puncte2 +
                ", puncte3=" + puncte3 +
                '}';
    }
}
