package de.ostfalia.view;
import de.ostfalia.se.boundary.OrderItemService;
import de.ostfalia.se.boundary.OrderService;
import de.ostfalia.se.entity.Customer;
import de.ostfalia.se.entity.Order;
import de.ostfalia.se.entity.OrderItem;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bean for the JSF Page 'allCustomers.xhtml'
 */
@Named
@ViewScoped
public class AllOrders implements Serializable {

    @Inject
    OrderService os;

    @Inject
    OrderItemService ois;
    private List<Order> orders;
    private List<OrderItem> oderItems;
    private Map<Order, List<OrderItem>> map;



    /**
     * Gets all customers from the orders table
     * and stores in the corresponding class attribute
     */
    @PostConstruct
    public void init(){
        this.orders = new ArrayList<>();
        this.oderItems=new ArrayList<>();
        this.map = new HashMap<>();


        this.orders = os.findAll();  //get all orders from the database
        for(int i = 0; i < this.orders.size(); i++){
            Customer customer =this.orders.get(i).getCustomer();  //get customer for each order
            this.oderItems = ois.findOrderItemByCustomer(customer, orders.get(i));
            this.map.put(this.orders.get(i), this.oderItems);
        }
    }



    //Getters and Setters

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


    public Map<Order, List<OrderItem>> getMap() {
        return map;
    }

    public void setMap(Map<Order, List<OrderItem>> map) {
        this.map = map;
    }
}

