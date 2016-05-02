package org.meiskalt7.crud;

import java.util.List;

public interface GenericDao<T> {
    void add(T t);

    void update(T t);

    void delete(int id);

    T get(int id);

    List<T> getAll();

    void deleteAll();
}
