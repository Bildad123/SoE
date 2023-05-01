package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.Brand;
import de.ostfalia.se.entity.Category;
import de.ostfalia.se.entity.Customer;
import de.ostfalia.se.entity.Product;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


import java.io.Serializable;
import java.util.List;

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
     * Returns the product with the corresponding name
     * @param name
     * @return Product
     */
    public Product findByName(String name){
        TypedQuery<Product> query = em.createQuery("select p from Product  p where p.name = :name", Product.class);
        query.setParameter("name", name);
        List<Product> product = query.getResultList();
        if(!product.isEmpty()){
            return product.get(0);
        }
        return null;
    }

    /**
     * Returns the product with the corresponding id
     * @param id
     * @return product
     */
    public Product findById(Long id){
        Product product = em.find(Product.class, id);
        return product;
    }

    public void delete(Product product) {
        Product mergedProduct = em.merge(product);
        em.remove(mergedProduct);
    }

    public void update(Product product) {
        em.merge(product);
    }

    public Brand findBrandByName(String brandName) {
        TypedQuery<Brand> query = em.createQuery("select b from Brand b where b.brandName = :brandName", Brand.class);
        query.setParameter("brandName", brandName);
        return query.getSingleResult();
    }

    public Category findCategoryByName(String categoryName) {
        TypedQuery<Category> query = em.createQuery("select c from Category c where c.categoryName = :categoryName", Category.class);
        query.setParameter("categoryName", categoryName);
        return query.getSingleResult();
    }

}
