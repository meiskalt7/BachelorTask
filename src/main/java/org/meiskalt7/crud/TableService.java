package org.meiskalt7.crud;

import org.meiskalt7.entity.Table;
import org.meiskalt7.servlets.Entity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class TableService extends Service<Table> {

    private static TableService tableService;

    private TableService() {

    }

    public static synchronized TableService getInstance() {
        if (tableService == null) {
            tableService = new TableService();
        }
        return tableService;
    }

    public Table get(int id) {
        return em.find(Table.class, id);
    }

    public List<Table> getAll() {
        return (List<Table>) em.createQuery("SELECT i FROM Table i").getResultList();
    }

    public void deleteAll() {
        for (Table cat : getAll()) {
            delete(cat.getId());
        }
    }

    @Override
    public void create(HttpServletRequest request) {
        Service tableService = Service.getService(Entity.TABLE);

        int number = Integer.parseInt(request.getParameter("number"));
        tableService.add(new Table(number, request.getParameter("table")));
    }

    @Override
    public void update(HttpServletRequest request, int id) {

    }
}
