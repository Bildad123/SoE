package de.ostfalia.view;
import de.ostfalia.se.boundary.CustomerService;
import de.ostfalia.se.boundary.OrderItemService;
import de.ostfalia.se.boundary.OrderService;
import de.ostfalia.se.boundary.ProductService;
import de.ostfalia.se.entity.Customer;
import de.ostfalia.se.entity.Order;
import de.ostfalia.se.entity.OrderItem;
import de.ostfalia.se.entity.Product;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Bean for the JSF Page 'orderForm.xhtml'
 */
@Named
@ViewScoped
public class OrderForm implements Serializable{

    @Inject
    ProductService ps;

    @Inject
    CustomerService cs;

    @Inject
    OrderService os;

    @Inject
    OrderItemService ois;


    private List<Customer> customers;
    private String selectedCustomer;
    private List<Product> products;

    private String selectedProduct;

    private List<OrderItem> orderItems;




    /**
     * Gets all customers from the database and saves in the attribute customers
     *
     * Gets all products from the database and saves in the attribute products
     *
     *
     */
    @PostConstruct
    public void init(){

        orderItems = new ArrayList<>();

        customers = cs.findAll();
        customers.add(0, new Customer("","",null, "",""));

        products = ps.findAll();
        products.add(0, new Product("", null));
    }


    /**
     * Updates oderItems
     *
     * The Update could be increasing the quantity,
     * reducing the quantity or deleting an order Item
     * @param shouldAdd
     */
    public void updateOrderItems(boolean shouldAdd){
        if(selectedProduct != null && selectedProduct.length() > 0){
            Product product = ps.findByName(this.selectedProduct);

            if(shouldAdd){  // + Button was selected
                if(!this.selectedItemIsPresent()){
                    if( this.orderItems.isEmpty() ){
                        OrderItem item = new OrderItem(product);
                        this.orderItems.add(item);   //very first order
                    }  else{
                        this.addOrderItem(product);  //next orders
                    }

                }else{
                    this.increaseQuantity(product);   //adding order quantity
                }
            } else{      // - Button was selected
                if(this.selectedItemIsPresent()){
                    this.decreaseQuantity(product);

                }

            }
        }

    }

    /**
     * Adds an orderItem
     *
     * Creates a new Order and adds to the attribute orderItems
     * @param product
     */
    public void addOrderItem(Product product){
        this.orderItems.add(new OrderItem(product ));
    }

    /**
     * Checks if the selectedOrder is already present
     *
     * Important for determining if an orderItem is to be added or
     * If the quantity of the existing orderItem is to be incremented
     *
     * @return true/false
     */
    public boolean selectedItemIsPresent(){
        for(int i = 0; i < this.orderItems.size(); i++){
            String name = this.orderItems.get(i).getProduct().getName();
            if(this.selectedProduct.equals(name)){
                return true;
            }
        }
        return false;
    }

    /**
     * Increases the quantity of an orderItem
     * @param product
     */
    public void increaseQuantity(Product product){
        for(int j = 0; j < this.orderItems.size(); j++){
            String name = this.orderItems.get(j).getProduct().getName();
            if(product != null && product.getName().equals(name)){
                this.orderItems.get(j).setQuantity( this.orderItems.get(j).getQuantity() + 1 );
            }
        }
    }

    /**
     * Reduces the quantity of an orderItem
     * @param product
     */
    public void decreaseQuantity(Product product){
        for(int i = 0; i < this.orderItems.size(); i++){
            String name = this.orderItems.get(i).getProduct().getName();
            if(product != null && product.getName().equals(name)){
                this.orderItems.get(i).setQuantity( this.orderItems.get(i).getQuantity() - 1 );
                if(this.orderItems.get(i).getQuantity() <= 0){
                    this.deleteItem(this.orderItems.get(i));   //delete item if number is 0
                }
            }
        }
    }


    /**
     * Removes an orderItem from the attribute orderItems
     *
     * Use case : Quantity of an orderItem is reduced to value less than or equal to 0
     * @param item
     */
    public void deleteItem(OrderItem item){
        this.orderItems.remove(item);
    }

    /**
     * Assigns an order to the corresponding customer and updates the tables in the database
     *
     * @return 'allOrders?faces-redirect=true' / null
     */
    @Transactional
    public String submitForm(){
        if(this.selectedCustomer != null && this.selectedCustomer.length() > 0){
            String[] name = this.selectedCustomer.split("   ");
            if(name.length > 1){
                Customer customer = cs.findByName(name[0], name[1]);


                Order order = new Order(customer);
                double totalPrice = 0;
                for(int i = 0; i < this.orderItems.size(); i++){
                  //  this.orderItems.get(i).setOrder(order);
                    totalPrice += this.orderItems.get(i).getQuantity() * this.orderItems.get(i).getListPrice();
                    this.ois.getEm().persist(this.orderItems.get(i));
                }

                os.getEm().persist(order);



                return "allOrders" + "?faces-redirect=true";
            }
        }
        return null;
    }





    //Getters and Setters
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(String selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(String selectedProduct) {
        this.selectedProduct = selectedProduct;
    }
}
