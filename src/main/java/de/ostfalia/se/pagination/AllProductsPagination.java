package de.ostfalia.se.pagination;

import de.ostfalia.se.entity.Product;

import java.util.List;

public class AllProductsPagination extends Pagination<Product>{

    private List<Product> products;

    public AllProductsPagination(List<Product> products) {
        this.products = products;
    }


    @Override
    public List<Product> loadContent() {
        return this.products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
