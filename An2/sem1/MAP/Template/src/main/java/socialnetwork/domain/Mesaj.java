package socialnetwork.domain;

import socialnetwork.domain.enums.Privatitate;

public class Mesaj extends Entity<Long>{
    Long u1;
    Privatitate privacy;
    Long u2;
    String mesaj;

    public Mesaj(Long u1, Privatitate privacy, Long u2, String mesaj) {
        this.u1 = u1;
        this.privacy = privacy;
        this.u2 = u2;
        this.mesaj = mesaj;
    }

    public Long getU1() {
        return u1;
    }

    public void setU1(Long u1) {
        this.u1 = u1;
    }

    public Privatitate getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privatitate privacy) {
        this.privacy = privacy;
    }

    public Long getU2() {
        return u2;
    }

    public void setU2(Long u2) {
        this.u2 = u2;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }
}
