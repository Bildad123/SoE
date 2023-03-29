package de.ostfalia.view;

import de.ostfalia.se.boundary.CustomerService;
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

    @Inject
    CustomerService cs;

    private List<Order> orders;

    private List<Customer> Customers;

    private List<Customer> customersWithOrders;

    private List<OrderItem> oderItems;

    private List< List<OrderItem>> ooii;



    /**
     * Gets all customers from the orders table
     * and stores in the corresponding class attribute
     */
    @PostConstruct
    public void init(){
        this.Customers = new ArrayList<>();
        this.customersWithOrders = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.oderItems=new ArrayList<>();
        this.ooii = new ArrayList<>();


        this.orders = os.findAll();  //get all orders from the database

        for(int i = 0; i < this.orders.size(); i++){
            Customer customer =this.orders.get(i).getCustomer();  //get customer for each order


                if( !this.customersWithOrders.contains(customer)  ){
                    this.customersWithOrders.add(customer) ;  //add to list od customersWithOrders
                }
                this.oderItems = ois.findOrderItemByCustomer(customer, orders.get(i));
                System.out.println("Query size : " + this.oderItems.size());
                this.ooii.add(this.oderItems);
        }

    }



    //Getters and Setters

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Customer> getCustomers() {
        return Customers;
    }

    public void setCustomers(List<Customer> customers) {
        Customers = customers;
    }

    public List<Customer> getCustomersWithOrders() {
        return customersWithOrders;
    }

    public void setCustomersWithOrders(List<Customer> customersWithOrders) {
        this.customersWithOrders = customersWithOrders;
    }

    public List<OrderItem> getOderItems() {
        return oderItems;
    }

    public void setOderItems(List<OrderItem> oderItems) {
        this.oderItems = oderItems;
    }

    public List<List<OrderItem>> getOoii() {
        return ooii;
    }

    public void setOoii(List<List<OrderItem>> ooii) {
        this.ooii = ooii;
    }
}

