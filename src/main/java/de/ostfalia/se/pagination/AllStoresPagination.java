package de.ostfalia.se.pagination;

import de.ostfalia.se.entity.Store;

import java.util.List;

public class AllStoresPagination extends Pagination<Store>{
    private List<Store> stores;

    public AllStoresPagination(List<Store> stores) {
        this.stores = stores;
    }

    @Override
    public List<Store> loadContent() {
        return this.stores;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }
}
