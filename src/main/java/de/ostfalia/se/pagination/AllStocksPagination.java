package de.ostfalia.se.pagination;

import de.ostfalia.se.entity.Stock;


import java.util.List;

public class AllStocksPagination extends Pagination<Stock>{
    private List<Stock> stocks;

    public AllStocksPagination(List<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public List<Stock> loadContent() {
        return this.stocks;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}
