package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.Customer;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.PersistenceContextType.TRANSACTION;

/**
 * Class for performing CRUD Operations on the customers table
 */

@Stateless
public class CustomerService implements Serializable {
    @PersistenceContext (type = TRANSACTION )
    EntityManager em;

    /**
     * Saves a customer to the customers table
     * @param customer
     */
    public void save(Customer customer){
        em.persist(customer);
    }

    /**
     * Returns the all customers in the customer table
     * @return List<Customer>
     */
    public List<Customer> findAll(){
        TypedQuery<Customer> query = em.createQuery(
                "select c from Customer c ", Customer.class
        );
        return query.getResultList();
    }

    /**
     * Returns customer with corresponding id
     * @param id
     * @return customer
     */
    public Customer findById(Long id){
        Customer customer = em.find(Customer.class, id);
        return customer;
    }

    public void delete(Customer customer) {
        Customer detachedCustomer = em.merge(customer);
        em.remove(detachedCustomer);
    }

    public void update(Customer customer) {
        em.merge(customer);
    }

}
