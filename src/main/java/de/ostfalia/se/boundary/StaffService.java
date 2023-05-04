package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.Customer;
import de.ostfalia.se.entity.Staff;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.PersistenceContextType.TRANSACTION;

/**
 * Class for performing CRUD Operations on the staff table
 */
@Stateless
public class StaffService implements Serializable {
    @PersistenceContext(type = TRANSACTION )
    EntityManager em;

    /**
     * Returns the all staff in the staff table
     * @return List<Staff>
     */
    public List<Staff> findAll(){
        TypedQuery<Staff> query = em.createQuery(
                "select c from Staff c ", Staff.class
        );
        return query.getResultList();
    }

    /**
     * Returns staff with corresponding id
     * @param id
     * @return staff
     */
    public Staff findById(Long id){
        Staff staff = em.find(Staff.class, id);
        return staff;
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
        Staff detachedStaff = em.merge(staff);
        em.remove(detachedStaff);
    }

    /**
     * Update a staff to the staff table
     * @param staff
     */
    public void update(Staff staff) {
        em.merge(staff);
    }

}
