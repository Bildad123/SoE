package de.ostfalia.se.boundary;
import de.ostfalia.se.entity.OrderItem;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


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

}
