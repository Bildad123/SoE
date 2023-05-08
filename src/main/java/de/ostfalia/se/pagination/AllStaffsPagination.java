package de.ostfalia.se.pagination;

import de.ostfalia.se.entity.Staff;

import java.util.List;

/**
 * Pagination clas for the JSF page 'allProduct.xhtml'
 */
public class AllStaffsPagination extends Pagination<Staff>{
    private List<Staff> staffs;
    public AllStaffsPagination(List<Staff> staffs) {
        this.staffs = staffs;
    }

    @Override
    public List<Staff> loadContent() {
        return this.staffs;
    }

    //Getters and Setters

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }
}
