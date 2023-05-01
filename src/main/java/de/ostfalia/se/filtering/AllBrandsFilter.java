package de.ostfalia.se.filtering;

import de.ostfalia.se.entity.Brand;
import de.ostfalia.se.entity.Store;

import java.util.function.Predicate;

/**
 * Class is used for filtering stores shown on the JSF page 'allStocks.xhtml'
 */
public class AllBrandsFilter implements Predicate<Brand> {

    private String searchText;

    /**
     /**
     * Method checks if
     * 1) searchText is contained in the name of the store OR
     * 2) name of store is contained in searchText
     * @param store the input argument
     * @return
     */
    @Override
    public boolean test(Brand brand) {
        return searchText.toLowerCase().contains(brand.getBrandName().toLowerCase())
                || brand.getBrandName().toLowerCase().contains(searchText.toLowerCase());
    }

    //Getters and Setters
    public String getSearchText() {
        return searchText;
    }
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
