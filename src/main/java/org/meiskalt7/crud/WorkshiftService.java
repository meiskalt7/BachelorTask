package org.meiskalt7.crud;

import org.meiskalt7.entity.Workshift;

import java.util.List;

public class WorkshiftService extends Service<Workshift> {

    private static WorkshiftService workshiftService;

    private WorkshiftService() {
    }

    public static synchronized WorkshiftService getInstance() {
        if (workshiftService == null) {
            workshiftService = new WorkshiftService();
        }
        return workshiftService;
    }

    public Workshift get(int id) {
        return em.find(Workshift.class, id);
    }

    public List<Workshift> getAll() {
        return (List<Workshift>) em.createQuery("SELECT w FROM Workshift w").getResultList();
    }

    public void deleteAll() {
        for (Workshift workshift : getAll()) {
            delete(workshift.getId());
        }
    }
}
