package de.ostfalia.view;

import de.ostfalia.se.boundary.ProductService;
import de.ostfalia.se.entity.Product;
import de.ostfalia.se.filtering.AllCustomersFilter;
import de.ostfalia.se.filtering.AllProductsFilter;
import de.ostfalia.se.pagination.AllProductsPagination;
import de.ostfalia.se.pagination.Pagination;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean for the JSF Page 'allProducts.xhtml'
 */
@Named
@ViewScoped
public class AllProducts implements Serializable {
    @Inject
    ProductService ps;
    private List<Product> products;

    private List<Product> filteredProducts;

    private AllProductsPagination pagination;

    private String searchText;

    private AllProductsFilter filter;

    /**
     * Gets all customers from the products table
     * and stores in the corresponding class attribute
     */
    @PostConstruct
    public void init(){
        products = ps.findAll();
        filteredProducts = ps.findAll();
        pagination = new AllProductsPagination(products);
        filter = new AllProductsFilter();
        this.pagination.doRefresh();
    }

    public void keypress() {
        filter.setSearchText(searchText);
        if(!searchText.isBlank()){
            filter.setSearchText(searchText);
            filteredProducts = products.stream().filter(c -> filter.test(c)).collect(Collectors.toList());
        } else{
            filteredProducts = new ArrayList<>();
            filteredProducts.addAll(filteredProducts);
        }
        this.pagination.setProducts(filteredProducts);
        this.pagination.setCurrentRows(0);
        this.pagination.setSelectedPage(1);
        this.pagination.doRefresh();
        System.out.println("search Text : " + searchText);
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

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public List<Product> getFilteredProducts() {
        return filteredProducts;
    }

    public void setFilteredProducts(List<Product> filteredProducts) {
        this.filteredProducts = filteredProducts;
    }

    public AllProductsFilter getFilter() {
        return filter;
    }

    public void setFilter(AllProductsFilter filter) {
        this.filter = filter;
    }
}
