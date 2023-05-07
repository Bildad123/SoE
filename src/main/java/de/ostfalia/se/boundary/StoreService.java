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

    public Store findById(Integer id){
        Store store = em.find(Store.class, id);
        return store;
    }

    public Store findByStoreName(String storeName) {
        TypedQuery<Store> query = em.createQuery(
                "select s from Store s where s.storeName = :storeName", Store.class
        );
        query.setParameter("storeName", storeName);
        return query.getSingleResult();
    }

    public void save(Store store){
        em.persist(store);
    }

    public Store findById(Long id){
        Store store = em.find(Store.class, id);
        return store;
    }

    public void delete(Store store) {
        Store detachedStore = em.merge(store);
        em.remove(detachedStore);
    }

    public void update(Store store) {
        em.merge(store);
    }
}
