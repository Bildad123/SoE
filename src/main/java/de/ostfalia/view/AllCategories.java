package de.ostfalia.view;

import de.ostfalia.se.boundary.CategoryService;
import de.ostfalia.se.entity.Category;
import de.ostfalia.se.filtering.AllCategoriesFilter;
import de.ostfalia.se.pagination.AllCategoriesPagination;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class AllCategories implements Serializable {

    @Inject
    CategoryService cs;
    private List<Category> categories;
    private List<Category> filteredCategories;
    private AllCategoriesPagination pagination;
    private AllCategoriesFilter filter;
    private String searchText;

    @PostConstruct
    public void init(){
        categories = cs.findAll();
        filteredCategories = cs.findAll();
        pagination = new AllCategoriesPagination(categories);
        filter = new AllCategoriesFilter();
        this.pagination.doRefresh();
    }

    public void keypress() {
        if(!searchText.isBlank()){;
            filter.setSearchText(searchText);
            this.filteredCategories = categories.stream().filter(c -> filter.test(c)).collect(Collectors.toList());

        } else{
            this.filteredCategories= new ArrayList<>();
            this.filteredCategories.addAll(categories);
        }
        this.pagination.setCategories(filteredCategories);
        this.pagination.setCurrentRows(0);
        this.pagination.setSelectedPage(1);
        this.pagination.doRefresh();
    }


    //Getters and Setters
    public List<Category> getCategories() {
        return categories;
    }
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    public AllCategoriesPagination getPagination() {
        return pagination;
    }
    public void setPagination(AllCategoriesPagination pagination) {
        this.pagination = pagination;
    }
    public List<Category> getFilteredCategories() {
        return filteredCategories;
    }
    public void setFilteredCategories(List<Category> filteredCategories) {
        this.filteredCategories = filteredCategories;
    }
    public String getSearchText() {
        return searchText;
    }
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
