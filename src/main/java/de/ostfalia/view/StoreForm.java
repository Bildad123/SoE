package de.ostfalia.view;

import de.ostfalia.se.boundary.ProductService;
import de.ostfalia.se.boundary.StoreService;
import de.ostfalia.se.entity.Brand;
import de.ostfalia.se.entity.Category;
import de.ostfalia.se.entity.Product;
import de.ostfalia.se.entity.Store;
import de.ostfalia.se.form.Form;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Bean for the JSF Page 'orderForm.xhtml'
 */
@Named
@ViewScoped
public class StoreForm implements Serializable {
    @Inject
    StoreService ss;
    @NotNull(message = "store name cannot be empty")
    private String storeName;
    @NotNull(message = "phone cannot be empty")
    private String phone;
    @NotNull(message = "email cannot be empty")
    private String email;
    @NotNull(message = "state cannot be empty")
    private String state;
    @NotNull(message = "city cannot be empty")
    private String city;
    @NotNull(message = "street cannot be empty")
    private String street;
    @NotNull(message = "zip code cannot be empty")
    private String zipCode;
    private Store store;
    private String operation;
    private Form form;


    @PostConstruct
    public void init(){
        Form form = new Form();
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if(id != null){
            this.store = ss.findById(Integer.valueOf(id));
        } else {
            this.store = new Store();
        }
        String operation = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("operation");
        this.operation = form.operationOnForm(operation, "Store");    //determines operation to be performed
        if (!this.operation.equals("Create Store")) {
            autoFillForm();
        }
    }

    public void autoFillForm(){
        storeName = store.getStoreName();
        phone = store.getPhone();
        email = store.getEmail();
        state = store.getState();
        city = store.getCity();
        street = store.getStreet();
        zipCode = store.getZipCode();
    }

    public void fillStore() {
        store.setStoreName(storeName);
        store.setPhone(phone);
        store.setEmail(email);
        store.setState(state);
        store.setCity(city);
        store.setStreet(street);
        store.setZipCode(zipCode);
    }

    public String submitForm() {
        if (operation.equals("Create Store")) {
            fillStore();
            ss.save(store);
        }
        if (operation.equals("Delete Store")) {
            ss.delete(store);
        }
        if (operation.equals("Edit Store")) {
            fillStore();
            ss.update(store);
        }
        return "allStores" + "?faces-redirect=true";
    }


    //Getters and Setters


    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

