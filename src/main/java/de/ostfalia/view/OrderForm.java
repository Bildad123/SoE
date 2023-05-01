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
        orderStatuses = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

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
            item.setListPrice(selectedProduct.getListPrice());
            item.setOrder(order);
            this.orderItems.add(item);
        }
    }

    public void updateCustomerSearchText(String s, Customer c){
        this.searchTextCustomers = s;
        this.selectedCustomer=c;
        showCustomerTable=false;
    }

    public void updateProductSearchText(String s){
        this.searchTextProducts=s;
        showProductTable=false;
    }
    public void updateOrderedDate(AjaxBehaviorEvent event) {
        this.orderDate= (LocalDate) event.getComponent().getAttributes().get("value");
    }

    public void updateRequiredDate(AjaxBehaviorEvent event) {
        this.requiredDate = (LocalDate) event.getComponent().getAttributes().get("value");
    }

    public void updateShippedDate(AjaxBehaviorEvent event) {
        this.shippedDate = (LocalDate) event.getComponent().getAttributes().get("value");
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

    public void showOrderToBeSaved(){
        Iterator<OrderItem> iterator = orderItems.iterator();
        while (iterator.hasNext()){
            OrderItem oi = iterator.next();
            System.out.println(oi);
        }
    }

    public void setItemIdForEachOrderItem(){
        Integer maxOrderItemId = orderItemService.maxOrderItemId();
        Iterator<OrderItem> iterator = orderItems.iterator();
        while (iterator.hasNext()){
            OrderItem oi = iterator.next();
            oi.setId(maxOrderItemId++);
        }
    }

    public void setOrderForEachOrderItem(){
        Iterator<OrderItem> iterator = orderItems.iterator();
        while (iterator.hasNext()){
            OrderItem oi = iterator.next();
            System.out.println("orderId : " + order.getId());
        }
    }

    public void saveOrderItems(){
        Iterator<OrderItem> iterator = orderItems.iterator();
        while (iterator.hasNext()){
            OrderItem oi = iterator.next();
            oi.setOrder(order);
            orderItemService.save(oi);
        }
    }

    /**
     * Assigns an order to the corresponding customer and updates the tables in the database
     *
     * @return 'allOrders?faces-redirect=true' / null
     */

    @Transactional
    public String submitForm(){
        if (operation.equals("Create Order")) {
            {
                orderService.save(order);
                fillOrder();

            }
            {
              //  setItemIdForEachOrderItem();
                 saveOrderItems();
            }

            showOrderToBeSaved();
            return null;
        }
        if (operation.equals("Delete Order")) {
            return null;
        }
        if (operation.equals("Edit Order")) {
            fillOrder();
            orderService.update(order);
            return null;
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
}