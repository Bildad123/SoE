package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.Customer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
public class CustomerService {

    @PersistenceContext
    EntityManager em;

    public void save(Customer customer){
        em.persist(customer);
    }

    public List<Customer> findAll(){
        TypedQuery<Customer> query = em.createQuery(
                "select c from Customer c ", Customer.class
        );
        return query.getResultList();
    }
}
