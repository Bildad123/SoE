package de.ostfalia.se.pagination;

import de.ostfalia.se.entity.Order;

import java.util.List;

/**
 * Pagination class for the JSF Page 'allOrders.xhtml'
 */
public class AllOrdersPagination extends Pagination<Order>{

    private List<Order> orders;

    public AllOrdersPagination(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public List<Order> loadContent() {
        return this.orders;
    }

    //Getters and Setters
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
