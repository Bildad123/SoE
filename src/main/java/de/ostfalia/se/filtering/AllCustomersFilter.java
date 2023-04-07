package de.ostfalia.se.filtering;


import de.ostfalia.se.entity.Customer;

import java.util.function.Predicate;

public class AllCustomersFilter implements Predicate<Customer> {

    private String searchText;

    @Override
    public boolean test(Customer customer) {
        return searchText.toLowerCase().contains(customer.getLastname().toLowerCase())
                || searchText.toLowerCase().contains(customer.getFirstname().toLowerCase())
                || customer.getFirstname().toLowerCase().contains(searchText.toLowerCase())
                || customer.getLastname().toLowerCase().contains(searchText.toLowerCase());
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
