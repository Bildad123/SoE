package de.ostfalia.view;

import de.ostfalia.se.boundary.ProductService;
import de.ostfalia.se.entity.Product;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 * Bean for the JSF Page 'orderForm.xhtml'
 */
@Named
@ViewScoped
public class ProductForm implements Serializable {

    @Inject
    ProductService ps;

    private String name;

    private double price;


    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

//    public String submitForm() {
//        Product p = new Product(this.name, this.price);
//        ps.save(p);
//        return "allProducts" + "?faces-redirect=true";
//    }
}
