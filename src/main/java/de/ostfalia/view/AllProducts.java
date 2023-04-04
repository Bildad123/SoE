package de.ostfalia.view;

import de.ostfalia.se.boundary.ProductService;
import de.ostfalia.se.entity.Product;
import de.ostfalia.se.pagination.AllProductsPagination;
import de.ostfalia.se.pagination.Pagination;
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

    private AllProductsPagination pagination;

    /**
     * Gets all customers from the products table
     * and stores in the corresponding class attribute
     */
    @PostConstruct
    public void init(){
        products = ps.findAll();
        pagination = new AllProductsPagination(products);
    }

    //Getters and Setters

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public AllProductsPagination getPagination() {
        return pagination;
    }

    public void setPagination(AllProductsPagination pagination) {
        this.pagination = pagination;
    }
}
