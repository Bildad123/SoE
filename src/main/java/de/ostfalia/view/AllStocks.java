package de.ostfalia.view;

import de.ostfalia.se.boundary.StockService;
import de.ostfalia.se.entity.Stock;
import de.ostfalia.se.pagination.AllStocksPagination;
import de.ostfalia.se.pagination.Pagination;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AllStocks implements Serializable {

    @Inject
    private  StockService ss;

    List<Stock> stocks;

    private AllStocksPagination pagination;


    @PostConstruct
    public void init(){
        stocks = ss.findAll();
        pagination = new AllStocksPagination(stocks);
    }


    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public AllStocksPagination getPagination() {
        return pagination;
    }

    public void setPagination(AllStocksPagination pagination) {
        this.pagination = pagination;
    }
}
