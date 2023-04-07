package de.ostfalia.se.filtering;

import de.ostfalia.se.entity.Stock;

import java.util.function.Predicate;

public class AllStocksFilter implements Predicate<Stock> {

    private String searchText;

    @Override
    public boolean test(Stock stock) {
        return searchText.toLowerCase().contains(stock.getProduct().getName().toLowerCase()) ||
                stock.getProduct().getName().toLowerCase().contains(searchText);

    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
