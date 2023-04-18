package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.Stock;
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
}
