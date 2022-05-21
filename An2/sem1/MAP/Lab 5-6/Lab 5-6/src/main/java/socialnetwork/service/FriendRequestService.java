package socialnetwork.service;

import socialnetwork.domain.Enums.Stare;
import socialnetwork.domain.FriendRequest;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FriendRequestService {
    private final Repository<Tuple<Tuple<Long, Long>, LocalDateTime>, FriendRequest> repo;

    /**
     * Metoda creaza un nou service pentru friendrequests care foloseste un Repository de friendrequests
     *
     * @param repo de tip Repository
     */
    public FriendRequestService(Repository<Tuple<Tuple<Long, Long>, LocalDateTime>, FriendRequest> repo) {
        this.repo = repo;
    }

    /**
     * Metoda adauga noua cerere de prietenie in repo doar daca aceasta nu exista deja
     *
     * @param friendRequest de tip FriendRequest, not null
     * @return entitatea care trebuia sa fie adaugata daca deja exista in repo, altfel null
     * @throws ValidationException if the entity is not valid
     */
    public FriendRequest addFriendRequest(FriendRequest friendRequest) throws ValidationException {
        FriendRequest pri = friendRequest;

        repo.findAll().forEach(x -> {
            if (x.getId().getLeft().getLeft() == friendRequest.getId().getLeft().getLeft() && x.getId().getLeft().getRight() == friendRequest.getId().getLeft().getRight() ||
                    x.getId().getLeft().getLeft() == friendRequest.getId().getLeft().getRight() && x.getId().getLeft().getRight() == friendRequest.getId().getLeft().getLeft()) {
                if (x.getState() == Stare.PENDING)
                    throw new ValidationException("Nu poti trimite de 2 ori acceasi cerere de prietenie");
            }
        });

        pri = repo.save(friendRequest);
        return pri;
    }

    /**
     * Metoda elimina cererea de prietenie dintre utilizatorii cu id1 si id2
     *
     * @param id1 de tip Long, trebuie sa fie a unui utilizator existent
     * @param id2 de tip Long, trebuie sa fie a unui utilizator existent
     * @return cererea de prietenie eliminata sau null daca aceasta nu exista
     */
    public FriendRequest deleteFriendRequest(Long id1, Long id2, LocalDateTime date) {
        //se incearca stergerea cererii prieteniei id1-id2 si id2-id1
        FriendRequest friendRequest = repo.delete(new Tuple<>(new Tuple<>(id1, id2), date));
        if (friendRequest == null)
            friendRequest = repo.delete(new Tuple<>(new Tuple<>(id2, id1), date));
        return friendRequest;
    }


    public FriendRequest acceptFriendRequest(Long id1, Long id2) throws ValidationException {

        List<FriendRequest> list = new ArrayList<FriendRequest>();

        for (FriendRequest fr : repo.findAll()) {
            if (fr.getId().getLeft().getLeft() == id1 && fr.getId().getLeft().getRight() == id2 ||
                    fr.getId().getLeft().getLeft() == id2 && fr.getId().getLeft().getRight() == id1){
                list.add(fr);
            }
        }

        list.sort(new Comparator<FriendRequest>() {
            @Override
            public int compare(FriendRequest o1, FriendRequest o2) {
                return o1.getId().getRight().compareTo(o2.getId().getRight());
            }
        });

        repo.delete(new Tuple<>(new Tuple<>(id2, id1), list.get(list.size()-1).getId().getRight()));
        repo.delete(new Tuple<>(new Tuple<>(id1, id2), list.get(list.size()-1).getId().getRight()));

        //System.out.println(list);

        FriendRequest f = list.get(list.size()-1);
        f.setState(Stare.ACCEPTED);

        repo.save(f);

        return f;
    }

    public FriendRequest declineFriendRequest(Long id1, Long id2) throws ValidationException {

        List<FriendRequest> list = new ArrayList<FriendRequest>();

        for (FriendRequest fr : repo.findAll()) {
            if (fr.getId().getLeft().getLeft() == id1 && fr.getId().getLeft().getRight() == id2 ||
                    fr.getId().getLeft().getLeft() == id2 && fr.getId().getLeft().getRight() == id1){
                list.add(fr);
            }
        }

        list.sort(new Comparator<FriendRequest>() {
            @Override
            public int compare(FriendRequest o1, FriendRequest o2) {
                return o1.getId().getRight().compareTo(o2.getId().getRight());
            }
        });

        repo.delete(new Tuple<>(new Tuple<>(id2, id1), list.get(list.size()-1).getId().getRight()));
        repo.delete(new Tuple<>(new Tuple<>(id1, id2), list.get(list.size()-1).getId().getRight()));

        FriendRequest f = list.get(list.size()-1);
        f.setState(Stare.DECLINED);

        repo.save(f);

        return f;
    }

    /**
     * @return toate entitatile friendRequest
     */
    public Iterable<FriendRequest> getAll() {
        return repo.findAll();
    }


}
