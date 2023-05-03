package de.ostfalia.se.filtering;

import de.ostfalia.se.entity.Stock;

import java.util.function.Predicate;

/**
 * Class is used for filtering stocks shown on the JSF page 'allStocks.xhtml'
 */
public class AllStocksFilter implements Predicate<Stock> {
    private String searchText;

    /**
     * Method checks if
     * 1) searchText is contained in the name of the product OR
     * 2) name of product is contained in searchText
     * @param stock the input argument
     * @return
     */
    @Override
    public boolean test(Stock stock) {
        if(stock != null && stock.getProduct() != null && stock.getProduct().getName() != null){
            return searchText.toLowerCase().contains(stock.getProduct().getName().toLowerCase()) ||
                    stock.getProduct().getName().toLowerCase().contains(searchText.toLowerCase());
        }
        return false;


    }

    //Getters and Setters
    public String getSearchText() {
        return searchText;
    }
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
