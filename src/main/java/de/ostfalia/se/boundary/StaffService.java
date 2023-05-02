package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.OrderItem;
import de.ostfalia.se.entity.Staff;
import jakarta.ejb.Stateful;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

import static jakarta.persistence.PersistenceContextType.TRANSACTION;
@Stateless
public class StaffService {
    @PersistenceContext(type = TRANSACTION )
    EntityManager em;

    public List<Staff> findAll(){
        TypedQuery<Staff> query = em.createQuery(
                "select s from Staff s ", Staff.class
        );
        return query.getResultList();
    }

    public Staff findById(Integer id){
        Staff staff = em.find(Staff.class, id);
        return staff;
    }

    public String groupsQuery(String email){
        String jpql = "SELECT CASE " +
                "           WHEN s.manager IS NULL THEN 'ADMIN' " +
                "           WHEN s.manager.id = 1 THEN 'USER1' " +
                "           ELSE 'USER2' " +
                "         END " +
                "FROM Staff s " +
                "WHERE s.email = :email";

        TypedQuery<String> query = em.createQuery(jpql, String.class);
        query.setParameter("email", email);

        if(query.getResultList().size() > 0){
            return query.getResultList().get(0);
        } else {
            return null;
        }
    }

    public String callerQuery(String email){
        TypedQuery<String> query = em.createQuery("SELECT s.phone FROM Staff s WHERE  s.email = :email", String.class);
        query.setParameter("email", email);
        if(query.getResultList().size() > 0){
            return query.getResultList().get(0).toString();
        } else {
            return null;
        }
    }

}
