package services;

import domain.Participant;
import domain.Proba;
import domain.Utilizator;

import java.util.List;

public interface IService {
    void login(Utilizator user, IAppObserver client) throws AppException;
    void logout(Utilizator user, IAppObserver client) throws AppException;

    Proba[] getProbe() throws AppException;
    Participant[] getParticipantiDupaProba(Proba p) throws AppException, AppException;

    Proba[] getProbeDupaParticipanti(Participant p) throws AppException;

    void Inscrie(Participant participant, Proba[] probe)throws AppException;
    List<Integer> getNrParticipanti() throws AppException;
}
