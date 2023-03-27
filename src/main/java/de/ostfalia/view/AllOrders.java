package de.ostfalia.view;

import de.ostfalia.se.boundary.CustomerService;
import de.ostfalia.se.boundary.OrderService;
import de.ostfalia.se.entity.Order;
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
public class AllOrders implements Serializable {

    @Inject
    OrderService os;

    List<Order> orders;

    /**
     * Gets all customers from the orders table
     * and stores in the corresponding class attribute
     */
    @PostConstruct
    public void init(){
        orders = os.findAll();

    }



    //Getters and Setters

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
