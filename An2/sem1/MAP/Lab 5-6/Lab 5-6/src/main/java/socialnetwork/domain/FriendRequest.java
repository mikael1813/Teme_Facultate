package socialnetwork.domain;

import socialnetwork.domain.Enums.Stare;

import java.time.LocalDateTime;

public class FriendRequest extends Entity<Tuple<Tuple<Long, Long>,LocalDateTime>> {
    Stare state;

    /**
     * @return the state of the friend request
     */
    public Stare getState() {
        return state;
    }

    /**
     * change the state of the friend request
     */
    public void setState(Stare state) {
        this.state = state;
    }

    /**
     * Metoda creaza o noua prietenie intre prietenii cu id-ul id1 si id2
     * si seteaza data ca fiind data la care s-au imprietenit
     *
     * @param id1 de tip Long
     * @param id2 de tip Long
     */
    public FriendRequest(Long id1, Long id2) {
        setId(new Tuple<>(new Tuple<>(id1, id2),LocalDateTime.now()));
        state = Stare.PENDING;
    }

    /**
     * Metoda creaza o noua prietenie intre prietenii cu id-ul id1 si id2
     * care s-au imprietenit la data d
     *
     * @param id1 de tip Long
     * @param id2 de tip Long
     * @param d   de tip LocalDateTime
     */
    public FriendRequest(Long id1, Long id2, LocalDateTime d, Stare state) {
        setId(new Tuple<>(new Tuple<>(id1, id2),d));
        this.state = state;
    }



    @Override
    public String toString() {
        return "FriendRequest{" +
                "from=" + getId().getLeft().getLeft() + ";" +
                "to=" + getId().getLeft().getRight() + ";" +
                "date=" + getId().getRight() +
                "stare=" + getState() +
                '}';
    }
}

