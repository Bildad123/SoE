package de.ostfalia.view;

import de.ostfalia.se.boundary.BrandService;
import de.ostfalia.se.boundary.CategoryService;
import de.ostfalia.se.boundary.ProductService;
import de.ostfalia.se.entity.Brand;
import de.ostfalia.se.entity.Category;
import de.ostfalia.se.entity.Product;
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
public class ProductForm implements Serializable {
    @Inject
    ProductService productService;
    @Inject
    BrandService brandService;
    @Inject
    CategoryService categoryService;
    @NotNull(message = "name cannot be empty")
    private String name;
    @NotNull(message = "price cannot be empty")
    private BigDecimal price;
    private String brandName;
    @NotNull(message = "price cannot be empty")
    private String modelYear;
    private String categoryName;
    private Product product;
    private String operation;
    private Form form;


    @PostConstruct
    public void init(){
        Form form = new Form();
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if(id != null){
            this.product = productService.findById(Integer.valueOf(id));
        } else {
            this.product = new Product();
        }
        String operation = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("operation");
        this.operation = form.operationOnForm(operation, "Product");    //determines operation to be performed
        if (!this.operation.equals("Create Product")) {
            autoFillForm();
        }
    }

    public void autoFillForm(){
        this.name = product.getName();
        this.price = product.getListPrice();
        this.brandName = product.getBrand().getBrandName();
        this.modelYear = product.getModelYear().toString();
        this.categoryName = product.getCategory().getCategoryName();
    }

    public void fillProduct() {
        product.setName(name);
        product.setListPrice(price);
        product.setBrand(brandService.findByBrandName(brandName));
        product.setModelYear(Integer.parseInt(modelYear));
        product.setCategory(categoryService.findByCategoryName(categoryName));
    }

    public String submitForm() {
        if (operation.equals("Create Product")) {
            fillProduct();
            productService.save(product);
        }
        if (operation.equals("Delete Product")) {
            productService.delete(product);
        }
        if (operation.equals("Edit Product")) {
            fillProduct();
            productService.update(product);
        }
        return "allProducts" + "?faces-redirect=true";
    }


    //Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelYear() {
        return modelYear;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
