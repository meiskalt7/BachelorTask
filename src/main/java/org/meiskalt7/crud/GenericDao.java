package org.meiskalt7.crud;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

interface GenericDao<T> {
    void add(T t);

    void update(T t);

    void delete(int id);

    T get(int id);

    List<T> getAll();

    void deleteAll();

    void create(HttpServletRequest request);

    void update(HttpServletRequest request, int id);
}
