package de.ostfalia.view;
import de.ostfalia.se.boundary.OrderItemService;
import de.ostfalia.se.boundary.OrderService;
import de.ostfalia.se.entity.Customer;
import de.ostfalia.se.entity.Order;
import de.ostfalia.se.entity.OrderItem;
import de.ostfalia.se.filtering.AllOrdersFilter;
import de.ostfalia.se.pagination.AllOrdersPagination;
import de.ostfalia.se.pagination.Pagination;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Bean for the JSF Page 'allCustomers.xhtml'
 */
@Named
@ViewScoped
public class AllOrders implements Serializable {

    @Inject
    OrderService os;

    private List<Order> orders;

    private List<Order> filteredOrders;


    private AllOrdersPagination pagination;

    private AllOrdersFilter filter;

    private String searchText;



    /**
     * Gets all customers from the orders table
     * and stores in the corresponding class attribute
     */
    @PostConstruct
    public void init(){
        this.orders = os.findAll();  //get all orders from the database
        this.filteredOrders = os.findAll();
        System.out.println("orders size : " + orders.size());

        this.pagination = new AllOrdersPagination(orders);
        filter = new AllOrdersFilter();
        this.pagination.doRefresh();
    }


    public void keypress() {
        if(!searchText.isBlank()){;
            filter.setSearchText(searchText);
            this.filteredOrders = orders.stream().filter(c -> filter.test(c)).collect(Collectors.toList());
        } else{
            this.filteredOrders = new ArrayList<>();
            this.filteredOrders.addAll(this.orders);
        }
        this.pagination.setOrders(filteredOrders);
        this.pagination.setCurrentRows(0);
        this.pagination.setSelectedPage(1);
        this.pagination.doRefresh();
    }



    //Getters and Setters

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }



    public AllOrdersPagination getPagination() {
        return pagination;
    }

    public void setPagination(AllOrdersPagination pagination) {
        this.pagination = pagination;
    }

    public List<Order> getFilteredOrders() {
        return filteredOrders;
    }

    public void setFilteredOrders(List<Order> filteredOrders) {
        this.filteredOrders = filteredOrders;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}

