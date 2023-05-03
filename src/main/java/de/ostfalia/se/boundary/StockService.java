package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Class for performing CRUD operations on the stock table
 */
@Stateless
public class StockService {
    @PersistenceContext
    EntityManager em;

    /**
     * Returns all the stocks from the database
     * @return List<Stock>
     */
    public List<Stock> findAll(){
        TypedQuery<Stock> query = em.createQuery(
                "select s from Stock s ", Stock.class
        );
        return query.getResultList();
    }

    public void save(Stock stock){
        Store mergedStore = em.merge(stock.getStore());
        stock.setStore(mergedStore);
        em.persist(stock);
    }

    public Stock findByPks(StockPK stockPK){
        Stock stock = em.find(Stock.class, stockPK);
        return stock;
    }

    public void delete(Stock stock) {
        Stock mergedStock = em.merge(stock);
        em.remove(mergedStock);
    }

    public void update(Stock stock) {
        em.merge(stock);
    }
}
