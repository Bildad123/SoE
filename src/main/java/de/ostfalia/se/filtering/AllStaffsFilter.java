package de.ostfalia.se.filtering;


import de.ostfalia.se.entity.Staff;

import java.util.function.Predicate;

public class AllStaffsFilter implements Predicate<Staff> {
    private String searchText;

    @Override
    public boolean test(Staff staff) {

        if(staff != null && staff.getFirstName() != null && staff.getLastName() != null){
            return searchText.toLowerCase().contains(staff.getLastName().toLowerCase())
                    || searchText.toLowerCase().contains(staff.getFirstName().toLowerCase())
                    || staff.getFirstName().toLowerCase().contains(searchText.toLowerCase())
                    || staff.getLastName().toLowerCase().contains(searchText.toLowerCase());
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