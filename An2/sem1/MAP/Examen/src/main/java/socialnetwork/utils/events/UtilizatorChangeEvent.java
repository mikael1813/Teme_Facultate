package socialnetwork.utils.events;

import socialnetwork.domain.Utilizator;

public class UtilizatorChangeEvent implements Event {
    private ChangeEvent type;
    private Utilizator data, oldData;
    //private Mesaj data2,oldData2;

    public UtilizatorChangeEvent(ChangeEvent type, Utilizator data) {
        this.type = type;
        this.data = data;
    }

//    public UtilizatorChangeEvent(ChangeEvent type, Mesaj data2) {
//        this.type = type;
//        this.data2 = data2;
//    }
    public UtilizatorChangeEvent(ChangeEvent type, Utilizator data, Utilizator oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEvent getType() {
        return type;
    }

    public Utilizator getData() {
        return data;
    }

    public Utilizator getOldData() {
        return oldData;
    }

//    public Mesaj getData2() {
//        return data2;
//    }
//
//    public Mesaj getOldData2() {
//        return oldData2;
//    }
}