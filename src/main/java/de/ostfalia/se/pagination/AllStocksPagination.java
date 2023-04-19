package de.ostfalia.se.pagination;

import de.ostfalia.se.entity.Stock;


import java.util.List;

/**
 * Pagination class for the JSF Page 'allStocks.xhtml'
 */
public class AllStocksPagination extends Pagination<Stock>{
    private List<Stock> stocks;

    public AllStocksPagination(List<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public List<Stock> loadContent() {
        return this.stocks;
    }

    //Getters and Setters
    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}
