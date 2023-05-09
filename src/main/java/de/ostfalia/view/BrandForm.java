package de.ostfalia.view;

import de.ostfalia.se.boundary.BrandService;
import de.ostfalia.se.entity.Brand;
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
public class BrandForm implements Serializable {
    @Inject
    BrandService bs;
    @NotNull(message = "brand name cannot be empty")
    private String brandName;
    private Brand brand;
    private String operation;
    private Form form;


    @PostConstruct
    public void init(){
        Form form = new Form();
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if(id != null){
            this.brand = bs.findById(Integer.valueOf(id));
        } else {
            this.brand = new Brand();
        }
        String operation = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("operation");
        this.operation = form.operationOnForm(operation, "Brand");    //determines operation to be performed
        if (!this.operation.equals("Create Brand")) {
            autoFillForm();
        }
    }

    public void autoFillForm(){
        brandName = brand.getBrandName();
    }

    public void fillStore() {
        brand.setBrandName(brandName);
    }

    public String submitForm() {
        if (operation.equals("Create Brand")) {
            fillStore();
            bs.save(brand);
        }
        if (operation.equals("Delete Brand")) {
            bs.delete(brand);
        }
        return "allBrands" + "?faces-redirect=true";
    }


    //Getters and Setters

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

