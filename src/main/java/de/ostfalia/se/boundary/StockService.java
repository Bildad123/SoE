package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.Product;
import de.ostfalia.se.entity.Stock;
import de.ostfalia.se.entity.Store;
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

    public void update(Stock stock) {
        em.merge(stock);
    }

    public void save(Stock stock){
        em.persist(stock);
    }

    public Stock findByProductAndStore(Store store, Product product){
        String jpql = "select s from Stock s where s.product = :product and  s.store = :store";
        TypedQuery<Stock> query = em.createQuery(jpql, Stock.class);
        query.setParameter("store", store);
        query.setParameter("product", product);

        List<Stock> stocks = query.getResultList();
        if(stocks.size() > 0){
            return stocks.get(0);
        }
        return null;
    }

}
