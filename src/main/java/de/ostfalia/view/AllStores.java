package de.ostfalia.view;

import de.ostfalia.se.boundary.StoreService;
import de.ostfalia.se.entity.Store;
import de.ostfalia.se.filtering.AllStoresFilter;
import de.ostfalia.se.pagination.AllStoresPagination;
import de.ostfalia.se.pagination.Pagination;
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
public class AllStores implements Serializable {

    @Inject
    StoreService ss;

    private List<Store> stores;

    private List<Store> filteredStores;

    private AllStoresPagination pagination;

    private AllStoresFilter filter;

    private String searchText;

    @PostConstruct
    public void init(){
        stores = ss.findAll();
        filteredStores = ss.findAll();
        pagination = new AllStoresPagination(stores);
        filter = new AllStoresFilter();
        this.pagination.doRefresh();
    }

    public void keypress() {
        if(!searchText.isBlank()){;
            filter.setSearchText(searchText);
            this.filteredStores = stores.stream().filter(c -> filter.test(c)).collect(Collectors.toList());

        } else{
            this.filteredStores= new ArrayList<>();
            this.filteredStores.addAll(stores);
        }
        this.pagination.setStores(filteredStores);
        this.pagination.setCurrentRows(0);
        this.pagination.setSelectedPage(1);
        this.pagination.doRefresh();
        System.out.println("search Text : " + searchText);
    }




    //Getters and Setters

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }


    public AllStoresPagination getPagination() {
        return pagination;
    }

    public void setPagination(AllStoresPagination pagination) {
        this.pagination = pagination;
    }

    public List<Store> getFilteredStores() {
        return filteredStores;
    }

    public void setFilteredStores(List<Store> filteredStores) {
        this.filteredStores = filteredStores;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
