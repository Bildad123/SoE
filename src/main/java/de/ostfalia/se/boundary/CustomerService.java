package de.ostfalia.se.boundary;

import de.ostfalia.se.entity.Customer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

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
     * Returns the customer with the corresponding firstname and lastname
     * @param firstname
     * @param lastname
     * @return Customer
     */
    public Customer findByName(String firstname, String lastname){
        TypedQuery<Customer> query = em.createQuery("select C from Customer  c where c.firstname = :firstname AND c.lastname =: lastname", Customer.class);
        query.setParameter("firstname", firstname);
        query.setParameter("lastname", lastname);
        List<Customer> customer = query.getResultList();
        if(!customer.isEmpty()){
            return customer.get(0);
        }
        return null;
    }


    //Getter
    public EntityManager getEm() {
        return em;
    }
}
