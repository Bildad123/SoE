package de.ostfalia.view;

import de.ostfalia.se.boundary.StockService;
import de.ostfalia.se.entity.Stock;
import de.ostfalia.se.filtering.AllStocksFilter;
import de.ostfalia.se.pagination.AllStocksPagination;
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
public class AllStocks implements Serializable {

    @Inject
    private  StockService ss;

    private List<Stock> stocks;

    private List<Stock> filteredStocks;

    private AllStocksPagination pagination;

    private AllStocksFilter filter;

    private String searchText;


    @PostConstruct
    public void init(){
        stocks = ss.findAll();
        filteredStocks = ss.findAll();
        pagination = new AllStocksPagination(stocks);
        filter = new AllStocksFilter();
        this.pagination.doRefresh();
    }


    public void keypress() {
        if(!searchText.isBlank()){;
            filter.setSearchText(searchText);
            this.filteredStocks = stocks.stream().filter(c -> filter.test(c)).collect(Collectors.toList());

        } else{
            this.filteredStocks = new ArrayList<>();
            this.filteredStocks.addAll(this.stocks);
        }
        this.pagination.setStocks(filteredStocks);
        this.pagination.setCurrentRows(0);
        this.pagination.setSelectedPage(1);
        this.pagination.doRefresh();
        System.out.println("search Text : " + searchText);

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

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public List<Stock> getFilteredStocks() {
        return filteredStocks;
    }

    public void setFilteredStocks(List<Stock> filteredStocks) {
        this.filteredStocks = filteredStocks;
    }
}
