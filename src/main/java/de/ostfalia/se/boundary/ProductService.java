package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.PersistenceContextType.TRANSACTION;

/**
 * Class for performing CRUD Operations on the products table
 */
@Stateless
public class ProductService implements Serializable {
    @PersistenceContext (type = TRANSACTION )
    EntityManager em;

    /**
     * Saves a product in the products table
     * @param product
     */
    public void save(Product product){
        Brand mergedBrand = em.merge(product.getBrand());
        Category mergedCategory = em.merge(product.getCategory());
        product.setBrand(mergedBrand);
        product.setCategory(mergedCategory);

        em.persist(product);
    }

    /**
     * Returns all the products in the products table
     * @return List<Product>
     */
    public List<Product> findAll(){
        TypedQuery<Product> query = em.createQuery(
                "select p from Product p ", Product.class
        );
        return query.getResultList();
    }

    /**
     * Returns the product with the corresponding id
     * @param id
     * @return product
     */
    public Product findById(Integer id){
        Product product = em.find(Product.class, id);
        return product;
    }

    public Product findByName(String name) {
        TypedQuery<Product> query = em.createQuery(
                "select p from Product p where p.name = :name", Product.class
        );
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public void deleteFromBrand(Product product) {
        if (product == null) {
            return;
        }
        Brand detachedBrand = product.getBrand();
        detachedBrand.getProducts().remove(product);
        em.merge(detachedBrand);
    }

    public void deleteFromCategory(Product product) {
        if (product == null) {
            return;
        }
        Category detachedCategory = product.getCategory();
        detachedCategory.getProducts().remove(product);
        em.merge(detachedCategory);
    }

    public void delete(Product product) {
        deleteFromBrand(product);
        deleteFromCategory(product);
        Product mergedProduct = em.merge(product);
        em.remove(mergedProduct);
    }

    public void update(Product product) {
        em.merge(product);
    }

}
