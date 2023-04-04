package de.ostfalia.view;

import de.ostfalia.se.boundary.StoreService;
import de.ostfalia.se.entity.Store;
import de.ostfalia.se.pagination.AllStoresPagination;
import de.ostfalia.se.pagination.Pagination;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class AllStores implements Serializable {

    @Inject
    StoreService ss;

    private List<Store> stores;

    private AllStoresPagination pagination;

    @PostConstruct
    public void init(){
        stores = ss.findAll();
        pagination = new AllStoresPagination(stores);
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
}
