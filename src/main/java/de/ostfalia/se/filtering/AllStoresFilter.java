package de.ostfalia.se.filtering;

import de.ostfalia.se.entity.Store;

import java.util.function.Predicate;

public class AllStoresFilter implements Predicate<Store> {

    private String searchText;

    @Override
    public boolean test(Store store) {
        return searchText.toLowerCase().contains(store.getStoreName().toLowerCase())
                || store.getStoreName().toLowerCase().contains(searchText.toLowerCase());
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
