package de.ostfalia.view;

import de.ostfalia.se.boundary.CustomerService;
import de.ostfalia.se.entity.Customer;
import de.ostfalia.se.pagination.AllCustomersPagination;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private String searchText;


    private List<Customer> filteredCustomers;

    AllCustomers(){

    }

    /**
     * Gets all customers from the customers table
     * and stores in the corresponding class attribute
     */
    @PostConstruct
    public void init(){
        customers = cs.findAll();
        filteredCustomers = cs.findAll();
        pagination = new AllCustomersPagination(filteredCustomers);
    }

    public boolean predicate(Customer customer, String searchText){
        return searchText.toLowerCase().contains(customer.getLastname().toLowerCase())
                || searchText.toLowerCase().contains(customer.getFirstname().toLowerCase())
                || customer.getFirstname().toLowerCase().contains(searchText.toLowerCase())
                || customer.getLastname().toLowerCase().contains(searchText.toLowerCase());
    }





    public void keypress() {
        if(searchText != null){
            if(!searchText.isBlank()){;
                this.filteredCustomers = customers.stream().filter(c -> predicate(c,searchText)).collect(Collectors.toList());

            } else{
                this.filteredCustomers = new ArrayList<>();
                this.filteredCustomers.addAll(this.customers);
            }
            this.pagination.setCustomers(filteredCustomers);
            this.pagination.setCurrentRows(0);
            this.pagination.setSelectedPage(1);
            this.pagination.doRefresh();
            System.out.println("search Text : " + searchText);
        }

    }





    //Getter
    public List<Customer> getCustomers() {
        return customers;
    }

    public AllCustomersPagination getPagination() {
        return pagination;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public List<Customer> getFilteredCustomers() {
        return filteredCustomers;
    }

    public void setFilteredCustomers(List<Customer> filteredCustomers) {
        this.filteredCustomers = filteredCustomers;
    }
}
