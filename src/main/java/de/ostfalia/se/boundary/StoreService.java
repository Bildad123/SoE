package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.Store;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
public class StoreService {
    @PersistenceContext
    EntityManager em;

    /**
     * Returns all the stores in the database
     *
     * @return List<Store>
     */
    public List<Store> findAll(){
        TypedQuery<Store> query = em.createQuery(
                "select s from Store s ", Store.class
        );
        return query.getResultList();
    }
}
