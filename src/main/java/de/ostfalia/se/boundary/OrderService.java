package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.Order;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.PersistenceContextType.TRANSACTION;

/**
 * Class for performing CRUD Operations on the orders table
 */
@Stateless
public class OrderService implements Serializable {
    @PersistenceContext (type = TRANSACTION )
    EntityManager em;

    /**
     * Saves the order in the database
     * @param order
     */
    public void save(Order order){
        em.persist(order);
    }


    /**
     * Returns all orders in the orders table
     *
     * @return List<Order>
     */
    public List<Order> findAll(){
        TypedQuery<Order> query = em.createQuery(
                "select co from Order co ", Order.class
        );
        return query.getResultList();
    }

    public Order findById(Long id){
        Order product = em.find(Order.class, id);
        return product;
    }


    public void delete(Order order) {
        Order detachedOrder = em.merge(order);
        em.remove(detachedOrder);
    }

    public void update(Order order) {
        em.merge(order);
    }


}
