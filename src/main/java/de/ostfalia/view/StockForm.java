package de.ostfalia.view;

import de.ostfalia.se.boundary.ProductService;
import de.ostfalia.se.boundary.StockService;
import de.ostfalia.se.boundary.StoreService;
import de.ostfalia.se.entity.*;
import de.ostfalia.se.filtering.AllProductsFilter;
import de.ostfalia.se.form.Form;
import de.ostfalia.se.pagination.AllProductsPagination;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean for the JSF Page 'orderForm.xhtml'
 */
@Named
@ViewScoped
public class StockForm implements Serializable {
    @Inject
    StockService stockService;
    @Inject
    ProductService productService;
    @Inject
    StoreService storeService;
    @NotNull(message = "product name cannot be empty")
    private String productName;
    @NotNull(message = "store name cannot be empty")
    private String storeName;
    @NotNull(message = "quantity cannot be empty")
    private Integer quantity;
    private Stock stock;
    private String operation;
    private Form form;
    private String searchTextProducts;
    private Product selectedProduct;
    private List<Product> filteredProducts = new ArrayList<>();
    private AllProductsPagination paginationProducts;
    private AllProductsFilter filterForProducts;
    private boolean showProductTable;
    private List<Product> products;

    @PostConstruct
    public void init(){
        products = productService.findAll();
        filteredProducts = new ArrayList<>();
        paginationProducts = new AllProductsPagination(products);
        paginationProducts.setMaxTableRows(5);
        filterForProducts = new AllProductsFilter();
        paginationProducts.doRefresh();

        getOperationFromQueryParam();
    }

    public void getOperationFromQueryParam(){
        Form form = new Form();
        String productId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("productId");
        String storeId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("storeId");
        if(productId != null && storeId != null){
            Product product = productService.findById(Integer.valueOf(productId));
            Store store = storeService.findById(Integer.valueOf(storeId));
//            StockPK stockPK = new StockPK(product, store);
//            this.stock = stockService.findByPks(stockPK);
            this.stock = stockService.findByProductAndStore(product, store);
        } else {
            this.stock = new Stock();
        }
        String operation = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("operation");
        this.operation = form.operationOnForm(operation, "Stock");    //determines operation to be performed
        if (!this.operation.equals("Create Stock")) {
            autoFillForm();
        }
    }

    public void autoFillForm(){
        productName = stock.getProduct().getName();
        storeName = stock.getStore().getStoreName();
        quantity = stock.getQuantity();
        selectedProduct = stock.getProduct();
        searchTextProducts = stock.getProduct().getName();
    }

    public void fillStock() {
        stock.setProduct(productService.findByName(productName));
        stock.setStore(storeService.findByStoreName(storeName));
        stock.setQuantity(quantity);
    }

    public String submitForm() {
        if (operation.equals("Create Stock")) {
            fillStock();
            if (stockService.findByProductAndStore(stock.getProduct(), stock.getStore()) == null) {
                stockService.save(stock);
            } else {
                stockService.update(stock);
            }
        }
        if (operation.equals("Delete Stock")) {
            stockService.delete(stock);
        }
        if (operation.equals("Edit Stock")) {
            fillStock();
            stockService.update(stock);
        }
        return "allStocks" + "?faces-redirect=true";
    }

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

    public void updateProductSearchText(String s, Product p){
        this.searchTextProducts = s;
        this.selectedProduct = p;
        showProductTable=false;
    }

    //Getters and Setters

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public Form getForm() {
        return form;
    }
    public void setForm(Form form) {
        this.form = form;
    }

    public StockService getStockService() {
        return stockService;
    }

    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public String getSearchTextProducts() {
        return searchTextProducts;
    }

    public void setSearchTextProducts(String searchTextProducts) {
        this.searchTextProducts = searchTextProducts;
    }

    public List<Product> getFilteredProducts() {
        return filteredProducts;
    }

    public void setFilteredProducts(List<Product> filteredProducts) {
        this.filteredProducts = filteredProducts;
    }

    public AllProductsPagination getPaginationProducts() {
        return paginationProducts;
    }

    public void setPaginationProducts(AllProductsPagination paginationProducts) {
        this.paginationProducts = paginationProducts;
    }

    public boolean isShowProductTable() {
        return showProductTable;
    }

    public void setShowProductTable(boolean showProductTable) {
        this.showProductTable = showProductTable;
    }

    public AllProductsFilter getFilterForProducts() {
        return filterForProducts;
    }

    public void setFilterForProducts(AllProductsFilter filterForProducts) {
        this.filterForProducts = filterForProducts;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }
}

