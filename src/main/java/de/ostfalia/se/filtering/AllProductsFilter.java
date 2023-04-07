package de.ostfalia.se.filtering;

import de.ostfalia.se.entity.Product;

import java.util.function.Predicate;

public class AllProductsFilter implements Predicate<Product> {

    private String searchText;

    @Override
    public boolean test(Product product) {
        return product.getName().toLowerCase().contains(searchText.toLowerCase())
                || searchText.toLowerCase().contains(product.getName().toLowerCase());
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
