package de.ostfalia.se.filtering;

import de.ostfalia.se.entity.Product;

import java.util.function.Predicate;

/**
 * Class is used for filtering products shown on the JSF page 'allProducts.xhtml'
 */
public class AllProductsFilter implements Predicate<Product> {
    private String searchText;

    /**
     * Method checks if
     * 1) searchText is contained in the name of the product OR
     * 2) name of product is contained in searchText
     * @param product the input argument
     * @return true/false
     */
    @Override
    public boolean test(Product product) {
        if(product != null && product.getName() != null){
            return product.getName().toLowerCase().contains(searchText.toLowerCase())
                    || searchText.toLowerCase().contains(product.getName().toLowerCase());
        }
        return false;

    }

    //Getters and Setters
    public String getSearchText() {
        return searchText;
    }
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
