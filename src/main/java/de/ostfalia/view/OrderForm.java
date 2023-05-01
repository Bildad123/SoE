package de.ostfalia.view;
import de.ostfalia.se.boundary.*;
import de.ostfalia.se.entity.*;
import de.ostfalia.se.filtering.AllCustomersFilter;
import de.ostfalia.se.filtering.AllProductsFilter;
import de.ostfalia.se.form.Form;
import de.ostfalia.se.pagination.AllCustomersPagination;
import de.ostfalia.se.pagination.AllProductsPagination;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
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
    @Inject
    OrderService orderService;
    @Inject
    OrderItemService orderItemService;
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
    private Store selectedStore;
    private List<Staff> staffs;
    private Staff selectedStaff;
    private List<Integer> orderStatuses;
    private int selectedOrderStatus;
    private LocalDate orderDate;
    private LocalDate requiredDate;
    private LocalDate shippedDate;
    private Order order;
    private String operation;

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
        orderStatuses = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        getOperationFromQueryParam();
    }

    public void getOperationFromQueryParam(){
        Form form = new Form();
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if(id != null){
            this.order = orderService.findById(Integer.valueOf(id));
        } else {
            this.order = new Order();
        }
        String operation = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("operation");
        this.operation = form.operationOnForm(operation, "Order");    //determines operation to be performed
        if (!this.operation.equals("Create Order")) {
            autoFillForm();
        }
    }

    public void autoFillForm(){
        this.selectedCustomer = order.getCustomer();
        this.orderDate = order.getOrderDate();
        this.selectedOrderStatus = order.getOrderStatus();
        this.requiredDate = order.getRequiredDate();
        this.shippedDate = order.getRequiredDate();
        this.searchTextCustomers = order.getCustomer().getFirstname().concat("   ").concat(order.getCustomer().getLastname()).concat("      ").concat(order.getCustomer().getZip()).concat(", ").concat(order.getCustomer().getStreet());
        this.selectedStore = order.getStore();
        this.selectedStaff = order.getStaff();
        this.orderItems = orderItemService.findByOrderIdAndCustomerId(order.getId(), order.getCustomer().getId());

    }

    @Transactional
    public String submitForm(){

        switch (operation){
            case "Create Order" : {
                saveOrder();
                fillOrder();
                saveOrderItems();
               // showOrderToBeSaved();
                return null;
            }
            case "Edit Order" : {
                fillOrder();
                orderService.update(order);
                // showOrderToBeSaved();
                return null;
            }
        }
        return "dashboard.xhtml";
    }




    //------------------- PERSISTING ORDER / ORDER ITEMS START --------------------
    public void saveOrderItems(){
        Iterator<OrderItem> iterator = orderItems.iterator();
        while (iterator.hasNext()){
            OrderItem oi = iterator.next();
            oi.setOrder(order);
            orderItemService.save(oi);
        }

    }
    public void saveOrder(){
        orderService.save(order);
    }


    //------------------ PERSISTING ORDER / ORDER ITEMS END --------------------------


    // ----------- SETTING UP ASSOCIATION BETWEEN ORDER AND ORDER ITEMS START ----------
    public void fillOrder() {
        setItemIdForEachOrderItem();
        setOrderForEachOrderItem();
        order.setCustomer(selectedCustomer);
        order.setOrderDate(orderDate);
        order.setOrderStatus(selectedOrderStatus);
        order.setRequiredDate(requiredDate);
        order.setShippedDate(shippedDate);
        order.setStore(selectedStore);
        order.setStaff(selectedStaff);
    }

    //--------------------------------  DEBUG FUNCTION ----------------------------------
    /**
     * Prints the object to be persisted on the console.
     * For debug purposes
     */
    public void showOrderToBeSaved(){
        Iterator<OrderItem> iterator = orderItems.iterator();
        while (iterator.hasNext()){
            OrderItem oi = iterator.next();
            System.out.println(oi);
        }
    }
    /**
     * Sets an id to each orderItem
     * Since orderItem ids are not auto generated by the database
     */
    public void setItemIdForEachOrderItem(){
        Integer maxOrderItemId = orderItemService.maxOrderItemId();
        Iterator<OrderItem> iterator = orderItems.iterator();
        while (iterator.hasNext()){
            OrderItem oi = iterator.next();
            oi.setId(maxOrderItemId++);
        }
    }

    /**
     * Associates all orderItems to order
     */
    public void setOrderForEachOrderItem(){
        Iterator<OrderItem> iterator = orderItems.iterator();
        while (iterator.hasNext()){
            OrderItem oi = iterator.next();
        }
    }
    // ----------- SETTING UP ASSOCIATION BETWEEN ORDER AND ORDER ITEMS END ----------



    // -------------- ADDING AND CHANGING ORDER ITEMS ON JSF PAGE START -----------------

    /**
     * Adds an order item to the order item table on the jsf page
     * @param product
     */
    public void addOrderItems(Product product){
        this.selectedProduct = product;
        if(!this.selectedItemIsPresent()){
            OrderItem item = new OrderItem();
            item.setProduct(selectedProduct);
            item.setQuantity(1);
            item.setListPrice(selectedProduct.getListPrice());
            item.setOrder(order);
            this.orderItems.add(item);
        }
    }
    /**
     * verifies if selectedItem is present
     * @return
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
     * Increases the quantity of an orderItem on the orderItem table on the jsf page
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
     * Deletes an order item from the order item table on the jsf page
     * @param item
     */
    public void deleteItem(OrderItem item){
        showProductTable=false;
        this.orderItems.remove(item);
    }
    //---------------  ADDING AND CHANGING ORDER ITEMS ON JSF PAGE END ------------------------


   //------------------------------------ AJAX EVENTS START ----------------------------------

    /**
     * Input Text with search functionality for customers
     */
    public void keypressCustomer() {
        showCustomerTable=true;
        if( searchTextCustomers != null &&!searchTextCustomers.isBlank()){;
            filterForCustomers.setSearchText(searchTextCustomers);
            this.filteredCustomers = customers.stream().filter(c -> filterForCustomers.test(c)).collect(Collectors.toList());
        } else{
            this.filteredCustomers = new ArrayList<>();
        }
        this.showCustomerTable = this.filteredCustomers.size() != this.customers.size();
        this.paginationCustomer.setCustomers(filteredCustomers);
        this.paginationCustomer.setCurrentRows(0);
        this.paginationCustomer.setSelectedPage(1);
        this.paginationCustomer.doRefresh();
    }

    /**
     * Input Text with search functionality for products
     */
    public void keypressProduct() {
        showProductTable = true;
        filterForProducts.setSearchText(searchTextProducts);
        if(!searchTextProducts.isBlank()){
            filterForProducts.setSearchText(searchTextProducts);
            filteredProducts = products.stream().filter(c -> filterForProducts.test(c)).collect(Collectors.toList());
        } else{
            filteredProducts = new ArrayList<>();
        }
        this.showProductTable = this.filteredProducts.size() != this.products.size();

        this.paginationProducts.setProducts(filteredProducts);
        this.paginationProducts.setCurrentRows(0);
        this.paginationProducts.setSelectedPage(1);
        this.paginationProducts.doRefresh();
    }

    /**
     * Clicking on a row on the customer table on the jsf page
     * 1) Updates the customer search text on the jsf page
     * 2) Updates the selected customer in the backing bean
     * @param s
     * @param c
     */
    public void updateCustomerSearchText(String s, Customer c){
        this.searchTextCustomers = s;
        this.selectedCustomer=c;
        showCustomerTable=false;
    }
    /**
     * Clicking on a row on the product table on the jsf page
     * 1) updates the product search text on the jsf page
     * 2) updates displays/updates the orderItem table on the jsf page
     * @param s
     */
    public void updateProductSearchText(String s){
        this.searchTextProducts=s;
        showProductTable=false;
    }

    /**
     * Updates the orderedDate
     * @param event
     */
    public void updateOrderedDate(AjaxBehaviorEvent event) {
        this.orderDate= (LocalDate) event.getComponent().getAttributes().get("value");
    }

    /**
     * Updates the requiredDate
     * @param event
     */
    public void updateRequiredDate(AjaxBehaviorEvent event) {
        this.requiredDate = (LocalDate) event.getComponent().getAttributes().get("value");
    }

    /**
     * Updates the shippedDate
     * @param event
     */
    public void updateShippedDate(AjaxBehaviorEvent event) {
        this.shippedDate = (LocalDate) event.getComponent().getAttributes().get("value");
    }
    //--------------------------------- AJAX EVENTS END ----------------------------------------


    //----------------------------- GETTERS AND SETTERS START ------------------------------
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
    public Store getSelectedStore() {
        return selectedStore;
    }
    public void setSelectedStore(Store selectedStore) {
        this.selectedStore = selectedStore;
    }
    public Staff getSelectedStaff() {
        return selectedStaff;
    }
    public void setSelectedStaff(Staff selectedStaff) {
        this.selectedStaff = selectedStaff;
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
    public LocalDate getRequiredDate() {
        return requiredDate;
    }
    public void setRequiredDate(LocalDate requiredDate) {
        this.requiredDate = requiredDate;
    }
    public LocalDate getShippedDate() {
        return shippedDate;
    }
    public void setShippedDate(LocalDate shippedDate) {
        this.shippedDate = shippedDate;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }

    //--------------------------- GETTERS AND SETTER END --------------------------
}