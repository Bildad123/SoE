package de.ostfalia.view;

import de.ostfalia.se.boundary.BrandService;
import de.ostfalia.se.entity.Brand;
import de.ostfalia.se.filtering.AllBrandsFilter;
import de.ostfalia.se.pagination.AllBrandsPagination;
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
public class AllBrands implements Serializable {

    @Inject
    BrandService bs;
    private List<Brand> brands;
    private List<Brand> filteredBrands;
    private AllBrandsPagination pagination;
    private AllBrandsFilter filter;
    private String searchText;

    @PostConstruct
    public void init(){
        brands = bs.findAll();
        filteredBrands = bs.findAll();
        pagination = new AllBrandsPagination(brands);
        filter = new AllBrandsFilter();
        this.pagination.doRefresh();
    }

    public void keypress() {
        if(!searchText.isBlank()){;
            filter.setSearchText(searchText);
            this.filteredBrands = brands.stream().filter(c -> filter.test(c)).collect(Collectors.toList());

        } else{
            this.filteredBrands= new ArrayList<>();
            this.filteredBrands.addAll(brands);
        }
        this.pagination.setBrands(filteredBrands);
        this.pagination.setCurrentRows(0);
        this.pagination.setSelectedPage(1);
        this.pagination.doRefresh();
    }


    //Getters and Setters
    public List<Brand> getBrands() {
        return brands;
    }
    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
    public AllBrandsPagination getPagination() {
        return pagination;
    }
    public void setPagination(AllBrandsPagination pagination) {
        this.pagination = pagination;
    }
    public List<Brand> getFilteredBrands() {
        return filteredBrands;
    }
    public void setFilteredBrands(List<Brand> filteredBrands) {
        this.filteredBrands = filteredBrands;
    }
    public String getSearchText() {
        return searchText;
    }
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
