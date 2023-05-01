package de.ostfalia.se.pagination;

import de.ostfalia.se.entity.Category;

import java.util.List;

/**
 * Pagination class for the JSF page 'allCategories.xhtml'
 */
public class AllCategoriesPagination extends Pagination<Category>{
    private List<Category> categories;

    public AllCategoriesPagination(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public List<Category> loadContent() {
        return this.categories;
    }

    //Getter and Setters
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
