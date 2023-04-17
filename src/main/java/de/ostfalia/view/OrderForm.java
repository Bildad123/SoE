package de.ostfalia.view;
import de.ostfalia.se.boundary.CustomerService;
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

    private List<Customer> customers;
    private List<Customer> filteredCustomers;
    private Customer selectedCustomer;
    private List<Product> products;
    private List<Product> filteredProducts;
    //private String selectedProduct;
    private Product selectedProduct;
    private List<OrderItem> orderItems;
    private AllCustomersPagination paginationCustomer;
    private AllCustomersFilter filterForCustomers;
    private String searchTextCustomers;
    private String searchTextProducts;
    private AllProductsPagination paginationProducts;
    private AllProductsFilter filterForProducts;
    private boolean showCustomerTable;
    private boolean showProductTable;

    /**
     * Gets all customers from the database and saves in the attribute customers
     *
     * Gets all products from the database and saves in the attribute products
     *
     */
    @PostConstruct
    public void init(){
        orderItems = new ArrayList<>();
        customers = cs.findAll();
        filteredCustomers = new ArrayList<>();
        products = ps.findAll();
        filteredProducts = new ArrayList<>();
        paginationProducts = new AllProductsPagination(products);
        paginationCustomer = new AllCustomersPagination(customers);
        paginationProducts.setMaxTableRows(1);
        paginationCustomer.setMaxTableRows(1);
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
        this.selectedProduct = product;
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

    public void updateSelectedCustomer(Customer customer){
        this.selectedCustomer = customer;
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
            Product product = this.orderItems.get(i).getProduct();
            if(this.selectedProduct.equals(product)){
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
        if(this.selectedCustomer != null){
            Order order = new Order(selectedCustomer);
            for(int i = 0; i < this.orderItems.size(); i++){
                //this.ois.save(this.orderItems.get(i));   //Needed for iteration3
            }
            //os.save(order); Needed for iteration3
            return "allOrders" + "?faces-redirect=true";
        }
        return null;
    }


    public void keypressCustomer() {
        if( searchTextCustomers != null &&!searchTextCustomers.isBlank()){;
            filterForCustomers.setSearchText(searchTextCustomers);
            this.filteredCustomers = customers.stream().filter(c -> filterForCustomers.test(c)).collect(Collectors.toList());

        } else{
            this.filteredCustomers = new ArrayList<>();
            //this.filteredCustomers.addAll(this.customers);
        }
        this.showCustomerTable = this.filteredCustomers.size() != this.customers.size();
        this.paginationCustomer.setCustomers(filteredCustomers);
        this.paginationCustomer.setCurrentRows(0);
        this.paginationCustomer.setSelectedPage(1);
        this.paginationCustomer.doRefresh();
    }


    public void keypressProduct() {
        filterForProducts.setSearchText(searchTextProducts);
        if(!searchTextProducts.isBlank()){
            filterForProducts.setSearchText(searchTextProducts);
            filteredProducts = products.stream().filter(c -> filterForProducts.test(c)).collect(Collectors.toList());
        } else{
            filteredProducts = new ArrayList<>();
            //filteredProducts.addAll(this.products);
        }
        this.showProductTable = this.filteredProducts.size() != this.products.size();

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
    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }
    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
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
    public Product getSelectedProduct() {
        return selectedProduct;
    }
    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
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

    public boolean isShowCustomerTable() {
        return showCustomerTable;
    }

    public void setShowCustomerTable(boolean showCustomerTable) {
        this.showCustomerTable = showCustomerTable;
    }

    public boolean isShowProductTable() {
        return showProductTable;
    }

    public void setShowProductTable(boolean showProductTable) {
        this.showProductTable = showProductTable;
    }
}