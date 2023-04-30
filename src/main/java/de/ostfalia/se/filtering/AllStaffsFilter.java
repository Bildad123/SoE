package de.ostfalia.se.filtering;


import de.ostfalia.se.entity.Staff;

import java.util.function.Predicate;

public class AllStaffsFilter implements Predicate<Staff> {
    private String searchText;

    @Override
    public boolean test(Staff staff) {
        return searchText.toLowerCase().contains(staff.getLastName().toLowerCase())
                || searchText.toLowerCase().contains(staff.getFirstName().toLowerCase())
                || staff.getFirstName().toLowerCase().contains(searchText.toLowerCase())
                || staff.getLastName().toLowerCase().contains(searchText.toLowerCase());
    }

    //Getters and Setters
    public String getSearchText() {
        return searchText;
    }
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}