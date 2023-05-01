package de.ostfalia.se.boundary;
import de.ostfalia.se.entity.Customer;
import de.ostfalia.se.entity.Order;
import de.ostfalia.se.entity.OrderItem;
import de.ostfalia.se.entity.Product;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;


import java.util.List;

import static jakarta.persistence.PersistenceContextType.TRANSACTION;

@Stateless
public class OrderItemService {
    @PersistenceContext(type = TRANSACTION )
    EntityManager em;

    /**
     * Saves the orderItem in the database
     *
     * @param orderItem
     */
    public void save(OrderItem orderItem){
        em.persist(orderItem);
    }

    public List<OrderItem> findByOrderIdAndCustomerId(Integer orderId, Integer customerId ){
        TypedQuery<OrderItem> query = em.createQuery("select oi from OrderItem oi where oi.order.id = :orderId and oi.order.customer.id = :customerId", OrderItem.class);
        query.setParameter("orderId", orderId);
        query.setParameter("customerId", customerId);
        List<OrderItem> orderItems = query.getResultList();
        return orderItems;
    }

    public void delete(OrderItem orderItem) {
        OrderItem detachedOrder = em.merge(orderItem);
        em.remove(detachedOrder);
    }


    public Integer maxOrderItemId(){
        Query query = em.createNativeQuery("SELECT MAX(item_id) FROM order_items");
        Integer maxOrderItemId = (Integer) query.getSingleResult();
        return maxOrderItemId;
    }

}
