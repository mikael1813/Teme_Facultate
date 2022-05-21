package domain;

import java.io.Serializable;

public class Game extends Entity<Integer> implements Serializable {
    Integer u1;
    Integer u2;
    Integer u3;
    String cuv1;
    String cuv2;
    String cuv3;

    public String getCuv1() {
        return cuv1;
    }

    public void setCuv1(String cuv1) {
        this.cuv1 = cuv1;
    }

    public String getCuv2() {
        return cuv2;
    }

    public void setCuv2(String cuv2) {
        this.cuv2 = cuv2;
    }

    public String getCuv3() {
        return cuv3;
    }

    public void setCuv3(String cuv3) {
        this.cuv3 = cuv3;
    }

    public int getU1() {
        return u1;
    }

    public void setU1(int u1) {
        this.u1 = u1;
    }

    public int getU2() {
        return u2;
    }

    public void setU2(int u2) {
        this.u2 = u2;
    }

    public int getU3() {
        return u3;
    }

    public void setU3(int u3) {
        this.u3 = u3;
    }

    public Game() {

    }

    public Game(int u1, int u2, int u3) {
        this.u1 = u1;
        this.u2 = u2;
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

    public void add(Integer u, String str) {
        if (u1 == null) {
            u1 = u;
            cuv1 = str;
        } else if (u2 == null) {
            u2 = u;
            cuv2 = str;
        } else {
            u3 = u;
            cuv3 = str;
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "u1=" + u1 +
                ", u2=" + u2 +
                ", u3=" + u3 +
                ", cuv1='" + cuv1 + '\'' +
                ", cuv2='" + cuv2 + '\'' +
                ", cuv3='" + cuv3 + '\'' +
                '}';
    }
}
