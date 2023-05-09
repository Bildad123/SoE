package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.Brand;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
public class BrandService {
    @PersistenceContext
    EntityManager em;

    /**
     * Returns all the Brands in the database
     *
     * @return List<Brand>
     */
    public List<Brand> findAll(){
        TypedQuery<Brand> query = em.createQuery(
                "select s from Brand s ", Brand.class
        );
        return query.getResultList();
    }

    public void save(Brand brand){
        em.persist(brand);
    }

    public Brand findById(Integer id){
        Brand brand = em.find(Brand.class, id);
        return brand;
    }

    public Brand findByBrandName(String brandName) {
        TypedQuery<Brand> query = em.createQuery("select b from Brand b where b.brandName = :brandName", Brand.class);
        query.setParameter("brandName", brandName);
        return query.getSingleResult();
    }

    public void delete(Brand brand) {
        Brand detachedBrand = em.merge(brand);
        em.remove(detachedBrand);
    }
}
