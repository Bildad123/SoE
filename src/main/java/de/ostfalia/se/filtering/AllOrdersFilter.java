package de.ostfalia.se.filtering;

import de.ostfalia.se.entity.Order;

import java.util.function.Predicate;

public class AllOrdersFilter implements Predicate<Order> {

    private String searchText;

    @Override
    public boolean test(Order order) {
        return order.getCustomer().getLastname().toLowerCase().contains(searchText.toLowerCase()) ||
                order.getCustomer().getFirstname().toLowerCase().contains(searchText.toLowerCase()) ||
                searchText.toLowerCase().contains(order.getCustomer().getFirstname().toLowerCase()) ||
                searchText.toLowerCase().contains(order.getCustomer().getLastname().toLowerCase());
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
