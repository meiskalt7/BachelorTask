package org.meiskalt7.crud;

import org.meiskalt7.entity.Categories;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Eiskalt on 12.10.2015.
 */
public class CategoriesService {

    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("test").createEntityManager();

    public Categories add(Categories categories) {
        em.getTransaction().begin();
        Categories categoriesFromDB = em.merge(categories);
        em.getTransaction().commit();
        return categoriesFromDB;
    }

    public void delete(int id) {
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }

    public Categories get(int id) {
        return em.find(Categories.class, id);
    }

    public void update(Categories categories) {
        em.getTransaction().begin();
        em.merge(categories);
        em.getTransaction().commit();
    }

    public List<Categories> getAll() {
        TypedQuery<Categories> namedQuery = em.createNamedQuery("Categories.getAll", Categories.class);
        return namedQuery.getResultList();
    }

    public void deleteAll() {
        for( Categories cat : getAll()) {
            delete(cat.getId());
        }
    }

    public int getId(String category) {
        //List<Integer> query = em.createQuery("SELECT id FROM Categories WHERE name = '" + category + "'").getResultList().get(0);
        //List<Integer> query = em.createQuery("SELECT id FROM Categories WHERE name = 'Printer'").getResultList();
        //em.createQuery("DELETE FROM Categories");
        //for(Integer cat:query) System.out.println(cat.toString());
        return (Integer)em.createQuery("SELECT id FROM Categories WHERE name = '" + category + "'").getResultList().get(0);
    }


}
