package ca.csfoy.servicesweb.camarchedoc.domain;

import java.util.List;

public interface Repository<T, U> {
    T getById(U id);

    List<T> getAll();

    T create(T event);

    void modify(U id, T event);

    void delete(U id);
}
