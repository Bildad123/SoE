package de.ostfalia.view;

import de.ostfalia.se.boundary.CustomerService;
import de.ostfalia.se.boundary.OrderItemService;
import de.ostfalia.se.entity.Customer;
import de.ostfalia.se.entity.Order;
import de.ostfalia.se.entity.OrderItem;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.Set;

@Named
@RequestScoped
public class OrderItems {
    @Inject
    OrderItemService ois;

    @Inject
    CustomerService cs;

    List<OrderItem> orderItems;


    Customer customer;

    @PostConstruct
    public void  init(){
        String customerId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("customerId");
        customer = cs.findById(Long.parseLong(customerId));
        String orderId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("orderId");
        orderItems = ois.findByOrderIdAndCustomerId(Long.parseLong(orderId), Long.parseLong(customerId));
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
