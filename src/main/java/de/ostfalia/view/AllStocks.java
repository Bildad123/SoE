package de.ostfalia.view;

import de.ostfalia.se.boundary.StockService;
import de.ostfalia.se.entity.Stock;
import de.ostfalia.se.pagination.Pagination;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class AllStocks extends Pagination<Stock> implements Serializable {

    @Inject
    private  StockService ss;

    List<Stock> stocks;


    @PostConstruct
    public void init(){
        stocks = ss.findAll();
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
