package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.Customer;
import de.ostfalia.se.entity.Order;
import de.ostfalia.se.entity.OrderItem;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

import static jakarta.persistence.PersistenceContextType.TRANSACTION;

@Stateless
public class OrderItemService {

    @PersistenceContext(type = TRANSACTION )
    EntityManager em;


    /**
     * Get all orderItems that belong to a given customer
     * @param customer
     * @return
     */
    public List<OrderItem> findOrderItemByCustomer(Customer customer, Order order){
        TypedQuery<OrderItem> query = em.createQuery(
                "select oi from OrderItem oi where oi.order.customer =:customer AND oi.order =: order", OrderItem.class
        );
        query.setParameter("customer", customer);
        query.setParameter("order", order);
        return query.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
