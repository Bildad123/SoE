package de.ostfalia.view;
import de.ostfalia.se.boundary.CustomerService;
import de.ostfalia.se.boundary.OrderItemService;
import de.ostfalia.se.boundary.OrderService;
import de.ostfalia.se.boundary.ProductService;
import de.ostfalia.se.entity.Customer;
import de.ostfalia.se.entity.Order;
import de.ostfalia.se.entity.OrderItem;
import de.ostfalia.se.entity.Product;
import de.ostfalia.se.filtering.AllCustomersFilter;
import de.ostfalia.se.filtering.AllProductsFilter;
import de.ostfalia.se.pagination.AllCustomersPagination;
import de.ostfalia.se.pagination.AllProductsPagination;
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
public class OrderFormTest implements Serializable{

    @Inject
    ProductService ps;

    @Inject
    CustomerService cs;

    @Inject
    OrderService os;

    @Inject
    OrderItemService ois;


    private List<Customer> customers;
    private List<Customer> filteredCustomers;
    private String selectedCustomer;
    private List<Product> products;
    private List<Product> filteredProducts;

    private String selectedProduct;

    private List<OrderItem> orderItems;


    private AllCustomersPagination paginationCustomer;

    private AllCustomersFilter filterForCustomers;

    private String searchTextCustomers;

    private String searchTextProducts;

    private AllProductsPagination paginationProducts;

    private AllProductsFilter filterForProducts;







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
        filteredCustomers = cs.findAll();


        products = ps.findAll();
        filteredProducts = ps.findAll();



        paginationProducts = new AllProductsPagination(products);
        paginationCustomer = new AllCustomersPagination(customers);

        paginationProducts.setMaxTableRows(3);
        paginationCustomer.setMaxTableRows(3);

        filterForProducts = new AllProductsFilter();
        filterForCustomers = new AllCustomersFilter();
        paginationProducts.doRefresh();
        paginationCustomer.doRefresh();
    }


    /**
     * Updates oderItems
     *
     * The Update could be increasing the quantity,
     * reducing the quantity or deleting an order Item
     * @param shouldAdd
     */
    public void updateOrderItems(boolean shouldAdd, Product product){
        System.out.println("updateOrderItems called");
        // if(selectedProduct != null && selectedProduct.length() > 0){
        //    Product product = ps.findByName(this.selectedProduct);
        this.selectedProduct = product.getName();

            if(shouldAdd){  // + Button was selected
                if(!this.selectedItemIsPresent()){
                    if( this.orderItems.isEmpty() ){
                        OrderItem item = new OrderItem(product);
                        this.orderItems.add(item);   //very first order
                        System.out.println("Very first order");
                    }  else{
                        this.addOrderItem(product);  //next orders
                        System.out.println("next orders");
                    }

                }else{
                    this.increaseQuantity(product);   //adding order quantity
                    System.out.println("Adding quantity");
                }
            } else{      // - Button was selected
                if(this.selectedItemIsPresent()){
                    this.decreaseQuantity(product);

                }

            }
   //     }

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


    public void keypressCustomer() {
        if( searchTextCustomers != null &&!searchTextCustomers.isBlank()){;
            filterForCustomers.setSearchText(searchTextCustomers);
            this.filteredCustomers = customers.stream().filter(c -> filterForCustomers.test(c)).collect(Collectors.toList());

        } else{
            this.filteredCustomers = new ArrayList<>();
            this.filteredCustomers.addAll(this.customers);
        }
        this.paginationCustomer.setCustomers(filteredCustomers);
        this.paginationCustomer.setCurrentRows(0);
        this.paginationCustomer.setSelectedPage(1);
        this.paginationCustomer.doRefresh();
        System.out.println("search Text Customers : " + searchTextCustomers);
    }


    public void keypressProduct() {
        filterForProducts.setSearchText(searchTextProducts);
        if(!searchTextProducts.isBlank()){
            filterForProducts.setSearchText(searchTextProducts);
            filteredProducts = products.stream().filter(c -> filterForProducts.test(c)).collect(Collectors.toList());
        } else{
            filteredProducts = new ArrayList<>();
            filteredProducts.addAll(this.products);
        }
        this.paginationProducts.setProducts(filteredProducts);
        this.paginationProducts.setCurrentRows(0);
        this.paginationProducts.setSelectedPage(1);
        this.paginationProducts.doRefresh();
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

    public List<Customer> getFilteredCustomers() {
        return filteredCustomers;
    }

    public void setFilteredCustomers(List<Customer> filteredCustomers) {
        this.filteredCustomers = filteredCustomers;
    }

    public List<Product> getFilteredProducts() {
        return filteredProducts;
    }

    public void setFilteredProducts(List<Product> filteredProducts) {
        this.filteredProducts = filteredProducts;
    }

    public AllCustomersPagination getPaginationCustomer() {
        return paginationCustomer;
    }

    public void setPaginationCustomer(AllCustomersPagination paginationCustomer) {
        this.paginationCustomer = paginationCustomer;
    }

    public AllCustomersFilter getFilterForCustomers() {
        return filterForCustomers;
    }

    public void setFilterForCustomers(AllCustomersFilter filterForCustomers) {
        this.filterForCustomers = filterForCustomers;
    }

    public String getSearchTextCustomers() {
        return searchTextCustomers;
    }

    public void setSearchTextCustomers(String searchTextCustomers) {
        this.searchTextCustomers = searchTextCustomers;
    }

    public String getSearchTextProducts() {
        return searchTextProducts;
    }

    public void setSearchTextProducts(String searchTextProducts) {
        this.searchTextProducts = searchTextProducts;
    }

    public AllProductsPagination getPaginationProducts() {
        return paginationProducts;
    }

    public void setPaginationProducts(AllProductsPagination paginationProducts) {
        this.paginationProducts = paginationProducts;
    }

    public AllProductsFilter getFilterForProducts() {
        return filterForProducts;
    }

    public void setFilterForProducts(AllProductsFilter filterForProducts) {
        this.filterForProducts = filterForProducts;
    }

}
