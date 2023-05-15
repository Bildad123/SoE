package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.Category;
import de.ostfalia.se.entity.Product;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
public class CategoryService {
    @PersistenceContext
    EntityManager em;

    /**
     * Returns all the categories in the database
     *
     * @return List<Category>
     */
    public List<Category> findAll(){
        TypedQuery<Category> query = em.createQuery(
                "select s from Category s", Category.class
        );
        return query.getResultList();
    }

    public void save(Category category){
        em.persist(category);
    }

    public Category findById(Integer id){
        Category category = em.find(Category.class, id);
        return category;
    }

    public Category findByCategoryName(String categoryName) {
        TypedQuery<Category> query = em.createQuery("select c from Category c where c.categoryName = :categoryName", Category.class);
        query.setParameter("categoryName", categoryName);
        return query.getSingleResult();
    }

    public void delete(Category category) {
        for (Product product : category.getProducts()) {
            product.setCategory(null);
            em.merge(product);
        }
        category.getProducts().clear();
        Category mergedCategory = em.merge(category);
        em.remove(mergedCategory);
    }
}
