package server;

import domain.*;
import repos.*;
import services.AppException;
import services.IAppObserver;
import services.IService;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class Service implements IService {
    private AdministratorRepository administratorRepository;
    private ClientRepository clientRepository;
    private LocRepository locRepository;
    private RezervareRepository rezervareRepository;
    private SpectacolRepository spectacolRepository;
    private Map<String, IAppObserver> loggedClients = new HashMap<>();

    public Service(AdministratorRepository administratorRepository, ClientRepository clientRepository, LocRepository locRepository, RezervareRepository rezervareRepository, SpectacolRepository spectacolRepository) {
        this.administratorRepository = administratorRepository;
        this.clientRepository = clientRepository;
        this.locRepository = locRepository;
        this.rezervareRepository = rezervareRepository;
        this.spectacolRepository = spectacolRepository;
    }

    public Utilizator login(Utilizator u, IAppObserver client) throws RemoteException,AppException {
        try {
            Client client1 = clientRepository.findByUser(u.getUser());
            if (loggedClients.get(client1.getUser()) != null)
                throw new AppException("User already logged in.");
            loggedClients.put(client1.getUser(), client);
            return client1;
        } catch (RuntimeException e) {

        }
        try {
            Administrator administrator = administratorRepository.findByUser(u.getUser());
            if (loggedClients.get(administrator.getUser()) != null)
                throw new AppException("User already logged in.");
            loggedClients.put(administrator.getUser(), client);
            return administrator;
        } catch (RuntimeException e) {

        }
        return null;
    }

    public void updateLoc(Loc loc) throws RemoteException {
        locRepository.update(loc);
    }

    public Iterable<Loc> getLocuri() throws RemoteException {
        Iterable<Loc> locs = locRepository.findAll();
        return locRepository.findAll();
    }

    public Iterable<Spectacol> getSpectacole() throws RemoteException {
        return spectacolRepository.findAll();
    }

    public Spectacol getSpectacolDeAzi() throws RemoteException {
        return spectacolRepository.filterByDate();
    }

    public void rezerva(Client client, Loc loc) throws RemoteException {
        rezervareRepository.save(new Rezervare(client, loc));
    }

    public void addShow(Spectacol spectacol) throws RemoteException {
        spectacolRepository.save(spectacol);
    }

    public void updateShow(Spectacol spectacol) throws RemoteException {
        spectacolRepository.update(spectacol);
    }

    public void deleteShow(Integer id) throws RemoteException {
        spectacolRepository.delete(id);
    }

    public void addLoc(Loc loc) throws RemoteException {
        locRepository.save(loc);
    }

    public void deleteLoc(Integer id) throws RemoteException {
        locRepository.delete(id);
    }
}
