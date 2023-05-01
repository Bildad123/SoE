package de.ostfalia.se.pagination;

import de.ostfalia.se.entity.Brand;
import de.ostfalia.se.entity.Store;

import java.util.List;

/**
 * Pagination class for the JSF page 'allBrands.xhtml'
 */
public class AllBrandsPagination extends Pagination<Brand>{
    private List<Brand> brands;

    public AllBrandsPagination(List<Brand> brands) {
        this.brands = brands;
    }

    @Override
    public List<Brand> loadContent() {
        return this.brands;
    }

    //Getter and Setters
    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}
