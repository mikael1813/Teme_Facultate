package service;

import domain.*;
import repos.*;

public class Service {
    private AdministratorRepository administratorRepository;
    private ClientRepository clientRepository;
    private LocRepository locRepository;
    private RezervareRepository rezervareRepository;
    private SpectacolRepository spectacolRepository;

    public Service(AdministratorRepository administratorRepository, ClientRepository clientRepository, LocRepository locRepository, RezervareRepository rezervareRepository, SpectacolRepository spectacolRepository) {
        this.administratorRepository = administratorRepository;
        this.clientRepository = clientRepository;
        this.locRepository = locRepository;
        this.rezervareRepository = rezervareRepository;
        this.spectacolRepository = spectacolRepository;
    }

    public Utilizator login(Utilizator u) {
        try{
            Client client = clientRepository.findByUser(u.getUser());
            return client;
        }
        catch (RuntimeException e){

        }
        try {
            Administrator administrator = administratorRepository.findByUser(u.getUser());
            return administrator;
        }
        catch (RuntimeException e){

        }
        return null;
    }

    public void updateLoc(Loc loc){
        locRepository.update(loc);
    }

    public Iterable<Loc> getLocuri() {
        return locRepository.findAll();
    }

    public Iterable<Spectacol> getSpectacole() {
        return spectacolRepository.findAll();
    }

    public Spectacol getSpectacolDeAzi() {
        return spectacolRepository.filterByDate();
    }

    public void rezerva(Client client, Loc loc) {
        rezervareRepository.save(new Rezervare(client, loc));
    }

    public void addShow(Spectacol spectacol) {
        spectacolRepository.save(spectacol);
    }

    public void updateShow(Spectacol spectacol) {
        spectacolRepository.update(spectacol);
    }

    public void deleteShow(Integer id) {
        spectacolRepository.delete(id);
    }

    public void addLoc(Loc loc) {
        locRepository.save(loc);
    }

    public void deleteLoc(Integer id) {
        locRepository.delete(id);
    }
}
