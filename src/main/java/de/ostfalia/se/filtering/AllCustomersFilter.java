package de.ostfalia.se.filtering;


import de.ostfalia.se.entity.Customer;

import java.util.function.Predicate;

/**
 * Class is used for filtering customers shown on the JSF page 'allCustomers.xhtml'
 */
public class AllCustomersFilter implements Predicate<Customer> {
    private String searchText;

    /**
     * Method checks if
     * 1) searchText is contained in the customer's firstname OR
     * 2) searchText is contained in the customer's lastname OR
     * 3) customer's firstname is contained in searchText
     * 4) customer's lastname is contained in searchText
     *
     * @param customer the input argument
     * @return true/false
     */
    @Override
    public boolean test(Customer customer) {
        if(customer != null && customer.getFirstname() != null && customer.getFirstname() != null){
            return searchText.toLowerCase().contains(customer.getLastname().toLowerCase())
                    || searchText.toLowerCase().contains(customer.getFirstname().toLowerCase())
                    || customer.getFirstname().toLowerCase().contains(searchText.toLowerCase())
                    || customer.getLastname().toLowerCase().contains(searchText.toLowerCase());
        }
        return false;

    }

    //Getters and Setters
    public String getSearchText() {
        return searchText;
    }
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
