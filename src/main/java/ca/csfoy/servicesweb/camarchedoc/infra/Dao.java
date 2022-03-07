package ca.csfoy.servicesweb.camarchedoc.infra;

import java.util.List;

public interface Dao<T, U> {

    boolean doesExist(U id);

    T selectById(U id);

    List<T> selectAll();

    T insert(T event);

    void update(U id, T event);

    void delete(U id);
}
