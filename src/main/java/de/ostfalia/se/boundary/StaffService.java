package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.Order;
import de.ostfalia.se.entity.Staff;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
}
