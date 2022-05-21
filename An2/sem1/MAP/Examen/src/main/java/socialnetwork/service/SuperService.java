package socialnetwork.service;

import socialnetwork.domain.Hotel;
import socialnetwork.domain.Location;
import socialnetwork.domain.SpecialOffer;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.enums.Privatitate;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.utils.events.ChangeEvent;
import socialnetwork.utils.events.UtilizatorChangeEvent;
import socialnetwork.utils.observer.Observable;
import socialnetwork.utils.observer.Observer;

import javax.swing.text.html.ListView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SuperService implements Observable<UtilizatorChangeEvent> {
    //private final UtilizatorService utilizatorService;
    //private UtilizatorChangeEvent eventUtilizator;
    final LocationService locationService;
    final HotelService hotelService;
    final SpecialOfferService specialOfferService;


    private List<Observer<UtilizatorChangeEvent>> observers = new ArrayList<>();

    public SuperService(LocationService locationService, HotelService hotelService,SpecialOfferService specialOfferService) {
        this.locationService = locationService;
        this.hotelService = hotelService;
        this.specialOfferService = specialOfferService;
    }

    public List<Hotel> getHotelsByLocation(Double location){
        List<Hotel> list = new ArrayList<>();
        hotelService.getAll().forEach(x->{
            if(x.getLocationId()==location){
                list.add(x);
            }
        });
        return list;
    }

    public List<SpecialOffer> getOffersByDate(LocalDate date,Hotel h){
        List<SpecialOffer> list = new ArrayList<>();
        specialOfferService.getAll().forEach(x->{
            if(date.compareTo(x.getStartDate())> 0 && date.compareTo(x.getEndDate())<0 && x.getHotelId() == h.getId())
                list.add(x);
        });

        return list;
    }

    public Iterable<Location> getLocations(){
        return locationService.getAll();
    }

    public Iterable<Hotel> getHotels(){
        return hotelService.getAll();
    }

    //public Iterable<Utilizator> getUsers() {
        //return utilizatorService.getAll();
    //}

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
