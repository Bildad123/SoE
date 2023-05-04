package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.Customer;
import de.ostfalia.se.entity.Stock;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.PersistenceContextType.TRANSACTION;

/**
 * Class for performing CRUD operations on the stock table
 */
@Stateless
public class StockService implements Serializable {
    @PersistenceContext (type = TRANSACTION )
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

    /**
     * Returns stock with corresponding id
     * @param id
     * @return stock
     */
    public Stock findById(Long id){
        Stock stock = em.find(Stock.class, id);
        return stock;
    }

    /**
     * Saves a stock to the stocks table
     * @param stock
     */
    public void save(Stock stock){
        em.persist(stock);
    }

    /**
     * Delete a stock to the stocks table
     * @param stock
     */
    public void delete(Stock stock) {
        Stock detachedStock = em.merge(stock);
        em.remove(detachedStock);
    }

    /**
     * Update a stock to the stocks table
     * @param stock
     */
    public void update(Stock stock) {
        em.merge(stock);
    }

}
