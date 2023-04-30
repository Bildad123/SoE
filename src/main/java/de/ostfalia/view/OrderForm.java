package de.ostfalia.view;
import de.ostfalia.se.boundary.CustomerService;
import de.ostfalia.se.boundary.ProductService;
import de.ostfalia.se.boundary.StaffService;
import de.ostfalia.se.boundary.StoreService;
import de.ostfalia.se.entity.*;
import de.ostfalia.se.filtering.AllCustomersFilter;
import de.ostfalia.se.filtering.AllProductsFilter;
import de.ostfalia.se.pagination.AllCustomersPagination;
import de.ostfalia.se.pagination.AllProductsPagination;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean for the JSF Page 'orderForm.xhtml'
 */
@Named
@ViewScoped
public class OrderForm implements Serializable{
    @Inject
    ProductService productService;
    @Inject
    CustomerService customerService;

    @Inject
    StoreService storeService;

    @Inject
    StaffService staffService;

    private List<Customer> customers;
    private List<Customer> filteredCustomers;
    private Customer selectedCustomer;
    private List<Product> products;
    private List<Product> filteredProducts;
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

    private List<Store> stores;
    private Long selectedStoreId;

    private List<Staff> staffs;

    private Long selectedStaffId;

    private List<Integer> orderStatuses;

    private int selectedOrderStatus;

    LocalDate orderDate;

    /**
     * Gets all customers from the database and saves in the attribute customers
     *
     * Gets all products from the database and saves in the attribute products
     *
     */
    @PostConstruct
    public void init(){
        orderItems = new ArrayList<>();
        customers = customerService.findAll();
        filteredCustomers = new ArrayList<>();
        products = productService.findAll();
        filteredProducts = new ArrayList<>();
        paginationProducts = new AllProductsPagination(products);
        paginationCustomer = new AllCustomersPagination(customers);
        paginationProducts.setMaxTableRows(5);
        paginationCustomer.setMaxTableRows(5);
        filterForProducts = new AllProductsFilter();
        filterForCustomers = new AllCustomersFilter();
        paginationProducts.doRefresh();
        paginationCustomer.doRefresh();

        stores= storeService.findAll();
        staffs=staffService.findAll();
        orderStatuses = new ArrayList<>();
        orderStatuses.add(3);
        orderStatuses.add(4);

    }


    /**
     * Updates oderItems
     *
     * The Update could be increasing the quantity,
     * reducing the quantity or deleting an order Item
     */
    public void addOrderItems(Product product){
        this.selectedProduct = product;
        if(!this.selectedItemIsPresent()){
            OrderItem item = new OrderItem();
            item.setProduct(selectedProduct);
            item.setQuantity(1);
            this.orderItems.add(item);
        }
    }

    public void updateCustomerSearchText(String s){
        this.searchTextCustomers = s;
        showCustomerTable=false;
    }

    public void updateProductSearchText(String s){
        this.searchTextProducts=s;
        showProductTable=false;
    }
    public void updateOrderedDate(AjaxBehaviorEvent event) {
        String newInputValue = (String) event.getComponent().getAttributes().get("value");
        System.out.println("orderDate : " + newInputValue);
    }

    public void updateRequiredDate(AjaxBehaviorEvent event) {
        String newInputValue = (String) event.getComponent().getAttributes().get("value");
        System.out.println("requiredDate : " + newInputValue);
    }

    public void updateShippedDate(AjaxBehaviorEvent event) {
        String newInputValue = (String) event.getComponent().getAttributes().get("value");
        System.out.println("shippedDate : " + newInputValue);
    }



    public void updateSelectedCustomer(Customer customer){
        this.selectedCustomer = customer;
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
            if(this.selectedProduct.getId().equals(product.getId())){
                return true;
            }
        }
        return false;
    }

    /**
     * Increases the quantity of an orderItem
     * @param product
     */
    public void changeQuantity(boolean increaseQuantity ,Product product){
        showProductTable=false;
        for(int i = 0; i < this.orderItems.size(); i++){
            Product p = this.orderItems.get(i).getProduct();
            if(product.getId().equals(p.getId())){
                if(increaseQuantity){
                    this.orderItems.get(i).setQuantity( this.orderItems.get(i).getQuantity() + 1 );
                } else{
                    this.orderItems.get(i).setQuantity( this.orderItems.get(i).getQuantity() - 1 );
                    if(this.orderItems.get(i).getQuantity() <= 0){
                        this.deleteItem(this.orderItems.get(i));
                    }
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
        showProductTable=false;
        this.orderItems.remove(item);
    }

    /**
     * Assigns an order to the corresponding customer and updates the tables in the database
     *
     * @return 'allOrders?faces-redirect=true' / null
     */
    @Transactional
    public String submitForm(){
        if(this.selectedCustomer != null && this.orderItems.size() > 0){
            Order order = new Order();
            order.setCustomer(selectedCustomer);
            //os.save(order); Needed for iteration3
            return "allOrders" + "?faces-redirect=true";
        }
        return null;
    }





























    public void keypressCustomer() {
        showCustomerTable=true;
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
        showProductTable = true;
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

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }

    public Long getSelectedStoreId() {
        return selectedStoreId;
    }

    public void setSelectedStoreId(Long selectedStoreId) {
        this.selectedStoreId = selectedStoreId;
    }

    public Long getSelectedStaffId() {
        return selectedStaffId;
    }

    public void setSelectedStaffId(Long selectedStaffId) {
        this.selectedStaffId = selectedStaffId;
    }

    public List<Integer> getOrderStatuses() {
        return orderStatuses;
    }

    public void setOrderStatuses(List<Integer> orderStatuses) {
        this.orderStatuses = orderStatuses;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public int getSelectedOrderStatus() {
        return selectedOrderStatus;
    }

    public void setSelectedOrderStatus(int selectedOrderStatus) {
        this.selectedOrderStatus = selectedOrderStatus;
    }
}