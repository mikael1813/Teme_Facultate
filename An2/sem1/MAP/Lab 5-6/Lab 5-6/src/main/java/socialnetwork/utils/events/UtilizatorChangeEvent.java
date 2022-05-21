package socialnetwork.utils.events;

import socialnetwork.domain.Utilizator;

public class UtilizatorChangeEvent implements Event {
    private ChangeEvent type;
    private Utilizator data, oldData;

    public UtilizatorChangeEvent(ChangeEvent type, Utilizator data) {
        this.type = type;
        this.data = data;
    }
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
}