package org.meiskalt7.crud;

import org.meiskalt7.entity.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class OrderService extends Service<Order> {

    private static OrderService orderService;

    private OrderService() {

    }

    public static synchronized OrderService getInstance() {
        if (orderService == null) {
            orderService = new OrderService();
        }
        return orderService;
    }

    public void refresh(Order order) {
        em.getTransaction().begin();
        em.refresh(order);
        em.getTransaction().commit();
    }

    public Order get(int id) {
        return em.find(Order.class, id);
    }

    public List<Order> getAll() {
        return (List<Order>) em.createQuery("SELECT o FROM Order o").getResultList();
    }

    public void deleteAll() {
        for (Order cat : getAll()) {
            delete(cat.getId());
        }
    }

    @Override
    public void create(HttpServletRequest request) {

    }

    @Override
    public void update(HttpServletRequest request, int id) {
        Order order = get(id);
        order.setEnded(true);
        update(order);
    }
}
