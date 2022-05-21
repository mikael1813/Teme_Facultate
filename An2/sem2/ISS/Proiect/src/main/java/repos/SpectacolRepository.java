package repos;

import domain.Spectacol;

public interface SpectacolRepository extends Repository<Integer, Spectacol> {
    Spectacol filterByDate();
}
