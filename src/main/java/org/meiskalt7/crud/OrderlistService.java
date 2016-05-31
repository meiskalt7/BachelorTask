package org.meiskalt7.crud;

import org.meiskalt7.entity.Orderlist;

import java.util.List;

public class OrderlistService extends Service<Orderlist> {

    private static OrderlistService orderlistService;

    private OrderlistService() {

    }

    public static synchronized OrderlistService getInstance() {
        if (orderlistService == null) {
            orderlistService = new OrderlistService();
        }
        return orderlistService;
    }

    public Orderlist get(int id) {
        return em.find(Orderlist.class, id);
    }

    public List<Orderlist> getAll() {
        return (List<Orderlist>) em.createQuery("SELECT o FROM Orderlist o").getResultList();
    }

    public void deleteAll() {
        for (Orderlist orderlist : getAll()) {
            delete(orderlist.getId());
        }
    }
}
