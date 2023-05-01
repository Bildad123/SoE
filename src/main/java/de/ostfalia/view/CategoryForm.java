package de.ostfalia.view;

import de.ostfalia.se.boundary.CategoryService;
import de.ostfalia.se.boundary.CategoryService;
import de.ostfalia.se.entity.Category;
import de.ostfalia.se.entity.Category;
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
public class CategoryForm implements Serializable {
    @Inject
    CategoryService cs;
    @NotNull(message = "category name cannot be empty")
    private String categoryName;
    private Category category;
    private String operation;
    private Form form;


    @PostConstruct
    public void init(){
        Form form = new Form();
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if(id != null){
            this.category = cs.findById(Long.valueOf(id));
        } else {
            this.category = new Category();
        }
        String operation = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("operation");
        this.operation = form.operationOnForm(operation, "Category");    //determines operation to be performed
        if (!this.operation.equals("Create Category")) {
            autoFillForm();
        }
    }

    public void autoFillForm(){
        categoryName = category.getCategoryName();
    }

    public void fillCategory() {
        category.setCategoryName(categoryName);
    }

    public String submitForm() {
        if (operation.equals("Create Category")) {
            fillCategory();
            cs.save(category);
        }
        if (operation.equals("Delete Category")) {
            cs.delete(category);
        }
        return "allCategories" + "?faces-redirect=true";
    }


    //Getters and Setters

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

