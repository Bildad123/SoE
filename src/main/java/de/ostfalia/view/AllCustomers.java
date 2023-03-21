package de.ostfalia.view;

import de.ostfalia.se.boundary.CustomerService;
import de.ostfalia.se.entity.Customer;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AllCustomers implements Serializable {

    private List<Customer> customers;
    @Inject
    CustomerService cs;

    AllCustomers(){

    }

    @PostConstruct
    public void init(){
        customers = cs.findAll();
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
