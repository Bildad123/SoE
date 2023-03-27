package de.ostfalia.view;

import de.ostfalia.se.boundary.ProductService;
import de.ostfalia.se.entity.Product;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

/**
 * Bean for the JSF Page 'allProducts.xhtml'
 */
@Named
@ViewScoped
public class AllProducts implements Serializable {
    @Inject
    ProductService ps;
    List<Product> products;

    /**
     * Gets all customers from the products table
     * and stores in the corresponding class attribute
     */
    @PostConstruct
    public void init(){
        products = ps.findAll();
    }

    //Getters and Setters

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
