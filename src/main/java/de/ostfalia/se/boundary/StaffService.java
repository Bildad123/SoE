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

    /**
     * Returns the all staff in the staff table
     * @return List<Staff>
     */
    public List<Staff> findAll(){
        TypedQuery<Staff> query = em.createQuery(
                "select s from Staff s join fetch s.store join fetch s.manager", Staff.class
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

    /**
     * Saves a staff to the staff table
     * @param staff
     */
    public void save(Staff staff){
        em.persist(staff);
    }

    /**
     * Delete a staff to the staff table
     * @param staff
     */
    public void delete(Staff staff) {
        Staff mergedStaff = em.merge(staff);
        em.remove(mergedStaff);
    }

    /**
     * Update a staff to the staff table
     * @param staff
     */
    public void update(Staff staff) {
        em.merge(staff);
    }

}
