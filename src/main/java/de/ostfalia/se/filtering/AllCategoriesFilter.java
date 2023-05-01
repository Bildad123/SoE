package de.ostfalia.se.filtering;

import de.ostfalia.se.entity.Category;

import java.util.function.Predicate;

/**
 * Class is used for filtering categories shown on the JSF page 'allStocks.xhtml'
 */
public class AllCategoriesFilter implements Predicate<Category> {

    private String searchText;

    /**
     /**
     * Method checks if
     * 1) searchText is contained in the name of the category OR
     * 2) name of category is contained in searchText
     * @param category the input argument
     * @return
     */
    @Override
    public boolean test(Category category) {
        return searchText.toLowerCase().contains(category.getCategoryName().toLowerCase())
                || category.getCategoryName().toLowerCase().contains(searchText.toLowerCase());
    }

    //Getters and Setters
    public String getSearchText() {
        return searchText;
    }
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
