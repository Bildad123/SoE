package de.ostfalia.se.filtering;

import de.ostfalia.se.entity.Order;

import java.util.function.Predicate;

/**
 * Class is used for filtering orders shown on the JSF page 'allOrders.xhtml'
 */
public class AllOrdersFilter implements Predicate<Order> {
    private String searchText;

    /**
     * Method checks if
     * 1) searchText is contained in the customer's firstname OR
     * 2) searchText is contained in the customer's lastname OR
     * 3) customer's firstname is contained in searchText
     * 4) customer's lastname is contained in searchText
     * @param order the input argument
     * @return
     */
    @Override
    public boolean test(Order order) {

        if(order.getCustomer() != null && order.getCustomer().getLastname() != null &&
          order.getCustomer().getFirstname() != null && order.getCustomer().getFirstname() != null){
            return order.getCustomer().getLastname().toLowerCase().contains(searchText.toLowerCase()) ||
                    order.getCustomer().getFirstname().toLowerCase().contains(searchText.toLowerCase()) ||
                    searchText.toLowerCase().contains(order.getCustomer().getFirstname().toLowerCase()) ||
                    searchText.toLowerCase().contains(order.getCustomer().getLastname().toLowerCase());
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
