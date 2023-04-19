package de.ostfalia.view;

import de.ostfalia.se.boundary.ProductService;
import de.ostfalia.se.entity.Product;
import de.ostfalia.se.form.Form;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * Bean for the JSF Page 'orderForm.xhtml'
 */
@Named
@ViewScoped
public class ProductForm implements Serializable {
    @Inject
    ProductService ps;
    @NotNull(message = "name cannot be empty")
    private String name;
    @NotNull(message = "price cannot be empty")
    private String price;
    private Product product;
    private String operation;
    private Form form;


    @PostConstruct
    public void init(){
        Form form = new Form();
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if(id != null){
            this.product = ps.findById(Long.valueOf(id));
        } else {
            this.product = new Product();
        }
        String operation = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("operation");
        this.operation = form.operationOnForm(operation, "Product");    //determines operation to be performed
        autoFillForm();
    }

    public void autoFillForm(){
        this.name = product.getName();
        this.price = product.getPrice()+"";
    }

    public String submitForm() {
        Product p = new Product(this.name, Double.valueOf(this.price) );
        //ps.save(p);  iteration3
        return "allProducts" + "?faces-redirect=true";
    }


    //Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
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

}
