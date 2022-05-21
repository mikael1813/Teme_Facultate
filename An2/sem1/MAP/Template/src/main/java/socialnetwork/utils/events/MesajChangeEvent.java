package socialnetwork.utils.events;

import socialnetwork.domain.Mesaj;
import socialnetwork.domain.Utilizator;

public class MesajChangeEvent implements Event{
    private ChangeEvent type;
    private Mesaj data, oldData;

    public MesajChangeEvent(ChangeEvent type, Mesaj data) {
        this.type = type;
        this.data = data;
    }
    public MesajChangeEvent(ChangeEvent type, Mesaj data, Mesaj oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEvent getType() {
        return type;
    }

    public Mesaj getData() {
        return data;
    }

    public Mesaj getOldData() {
        return oldData;
    }
}
