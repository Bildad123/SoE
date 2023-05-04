package de.ostfalia.view;

import de.ostfalia.se.boundary.StockService;
import de.ostfalia.se.entity.Customer;
import de.ostfalia.se.entity.Product;
import de.ostfalia.se.entity.Stock;
import de.ostfalia.se.entity.Store;
import de.ostfalia.se.form.Form;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Named
@ViewScoped
public class StockFrom implements Serializable {
    @Inject
    StockService ss;
    @NotNull(message = "Product cannot be empty")
    private Product product;
    @NotNull(message = "Store cannot be empty")
    private Store store;
    @NotNull(message = "Quantity cannot be empty")
    private Integer quantity;
    private String operation; //Can either be Create, Read, Edit or Delete
    private Stock stock;
    private Form form;

    public StockFrom(){
    }

    @PostConstruct
    public void init(){
        Form form = new Form();
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if(id != null){
            this.stock = ss.findById(Long.valueOf(id));
        } else {
            this.stock = new Stock();
        }
        String operation = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("operation");
        this.operation = form.operationOnForm(operation, "Customer");  //determines operation to be performed
        if (!this.operation.equals("Create Customer")) {
            autoFillForm();
        }
    }

    public void autoFillForm(){
        this.product = stock.getProduct();
        this.store = stock.getStore();
        this.quantity = stock.getQuantity();
    }

    public void fillStock(){
        stock.setProduct(product);
        stock.setStore(store);
        stock.setQuantity(quantity);
    }

    /**
     * creates a stock and saves to the stock table
     *
     * The user is then redirected to the JSF Page 'allStock.xhtml'
     *
     * @return 'allStock.xhtml?faces-redirect=true'
     */
    public String submitForm() {
        if (operation.equals("Create Stock")) {
            fillStock();
            ss.save(stock);
        }
        if (operation.equals("Delete Stock")) {
            ss.delete(stock);
        }
        if (operation.equals("Edit Stock")) {
            fillStock();
            ss.update(stock);
        }

        return "allStock" + "?faces-redirect=true";
    }

    //Getter and Setter
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
}
