package socialnetwork.service;

import socialnetwork.domain.Mesaj;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.enums.Activitate;
import socialnetwork.domain.enums.Pozitie;
import socialnetwork.domain.enums.Privatitate;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.utils.events.ChangeEvent;
import socialnetwork.utils.events.MesajChangeEvent;
import socialnetwork.utils.events.UtilizatorChangeEvent;
import socialnetwork.utils.observer.Observable;
import socialnetwork.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class SuperService implements Observable<UtilizatorChangeEvent> {
    private final UtilizatorService utilizatorService;
    private final MesajService mesajService;
    private UtilizatorChangeEvent eventUtilizator;
    private UtilizatorChangeEvent eventMesaj;

    private List<Observer<UtilizatorChangeEvent>> observers = new ArrayList<>();

    public SuperService(UtilizatorService utilizatorService, MesajService messageService) {
        this.utilizatorService = utilizatorService;
        this.mesajService = messageService;
    }

    public Mesaj addMesaj(Long u1, Privatitate privatitate,Long u2,String msg){
        Mesaj m = new Mesaj(u1,privatitate,u2,msg);
        Mesaj mesaj = mesajService.add(m);
        if (mesaj != null)
            throw new ValidationException("\t!!!Exista deja un mesaj cu acest id");
        eventMesaj = new UtilizatorChangeEvent(ChangeEvent.ADD,m);
        notifyObservers(eventMesaj);

        return mesaj;
    }

    public boolean canSefLeave() {
        boolean ok = true;
        for (Utilizator x : utilizatorService.getAll()) {
            if (x.getActivitate() == Activitate.ACTIV && x.getPozitie()!= Pozitie.SEF)
                ok = false;
        }
        return ok;
    }

    public void setUserInactiv(Utilizator u) {
        utilizatorService.setInactiv(u);
        eventUtilizator = new UtilizatorChangeEvent(ChangeEvent.UPDATE, u);
        notifyObservers(eventUtilizator);
    }

    public void setUserActiv(Utilizator u) {
        utilizatorService.setActiv(u);
        eventUtilizator = new UtilizatorChangeEvent(ChangeEvent.UPDATE, u);
        notifyObservers(eventUtilizator);
    }

    public Iterable<Utilizator> getUsers() {
        return utilizatorService.getAll();
    }

    public Iterable<Mesaj> getMessages() {
        return mesajService.getAll();
    }

    @Override
    public void addObserver(Observer<UtilizatorChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<UtilizatorChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(UtilizatorChangeEvent t) {
        observers.stream().forEach(x -> x.update(t));
    }
}
