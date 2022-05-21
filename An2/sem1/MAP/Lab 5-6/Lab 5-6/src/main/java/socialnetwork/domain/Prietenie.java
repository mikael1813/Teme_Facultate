package socialnetwork.domain;

import java.time.LocalDateTime;


public class Prietenie extends Entity<Tuple<Long,Long>> {

    LocalDateTime date;

    /**
     * Metoda creaza o noua prietenie intre prietenii cu id-ul id1 si id2
     * si seteaza data ca fiind data la care s-au imprietenit
     *
     * @param id1 de tip Long
     * @param id2 de tip Long
     */
    public Prietenie(Long id1, Long id2) {
        setId(new Tuple<>(id1, id2));
        date = LocalDateTime.now();
    }

    /**
     * Metoda creaza o noua prietenie intre prietenii cu id-ul id1 si id2
     * care s-au imprietenit la data d
     *
     * @param id1 de tip Long
     * @param id2 de tip Long
     * @param d   de tip LocalDateTime
     */
    public Prietenie(Long id1, Long id2, LocalDateTime d) {
        setId(new Tuple<>(id1, id2));
        date = d;
    }


    /**
     *
     * @return the date when the friendship was created
     */
    public LocalDateTime getDate() {
        return date;
    }


    @Override
    public String toString() {
        return "Prietenie{" +
                "p=" + getId().getLeft() + ";" +
                "p=" + getId().getRight() + ";" +
                "date=" + date.format(Constants.DATE_TIME_FORMATTER) +
                '}';
    }
}
