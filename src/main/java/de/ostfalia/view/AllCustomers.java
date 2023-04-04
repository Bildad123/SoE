package de.ostfalia.view;

import de.ostfalia.se.boundary.CustomerService;
import de.ostfalia.se.entity.Customer;
import de.ostfalia.se.pagination.AllCustomersPagination;
import de.ostfalia.se.pagination.Pagination;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

/**
 * Bean for the JSF Page 'allCustomers.xhtml'
 */
@Named
@ViewScoped
public class AllCustomers implements Serializable {
    @Inject
    CustomerService cs;

    private List<Customer> customers;

    private AllCustomersPagination pagination;

    AllCustomers(){

    }

    /**
     * Gets all customers from the customers table
     * and stores in the corresponding class attribute
     */
    @PostConstruct
    public void init(){
        customers = cs.findAll();
        pagination = new AllCustomersPagination(customers);
    }



    //Getter
    public List<Customer> getCustomers() {
        return customers;
    }

    public AllCustomersPagination getPagination() {
        return pagination;
    }
}
