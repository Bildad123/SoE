package de.ostfalia.se.pagination;

import de.ostfalia.se.entity.Customer;

import java.util.List;

public class AllCustomersPagination extends Pagination<Customer>{

    private List<Customer> customers;

    public AllCustomersPagination(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public List<Customer> loadContent() {
        return this.customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
