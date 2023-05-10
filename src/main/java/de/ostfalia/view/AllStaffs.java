package de.ostfalia.view;

import de.ostfalia.se.boundary.StaffService;
import de.ostfalia.se.entity.Staff;
import de.ostfalia.se.filtering.AllStaffsFilter;
import de.ostfalia.se.pagination.AllStaffsPagination;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean for the JSF Page 'allStaffs.xhtml'
 */
@Named
@ViewScoped
public class AllStaffs implements Serializable {
    @Inject
    StaffService ss;
    private List<Staff> staffs;
    private AllStaffsPagination pagination;
    private AllStaffsFilter filter;
    private String searchText;
    private List<Staff> filteredStaffs;

    private String pageView;
    private boolean toMasterView;

    AllStaffs(){
    }

    /**
     * Gets all staffs from the staffs table
     * and stores in the corresponding class attribute
     */
    @PostConstruct
    public void init(){
        staffs = ss.findAll();
        filteredStaffs = ss.findAll();
        pagination = new AllStaffsPagination(filteredStaffs);
        filter = new AllStaffsFilter();
        this.pagination.doRefresh();
        this.pageView = "To Detail Page";   //default
    }

    public void keypress() {
            if(!searchText.isBlank()){;
                filter.setSearchText(searchText);
                this.filteredStaffs = staffs.stream().filter(s -> filter.test(s)).collect(Collectors.toList());

            } else{
                this.filteredStaffs = new ArrayList<>();
                this.filteredStaffs.addAll(this.staffs);
            }
            this.pagination.setStaffs(filteredStaffs);
            this.pagination.setCurrentRows(0);
            this.pagination.setSelectedPage(1);
            this.pagination.doRefresh();
    }

    public void changeView(){
        toMasterView = !toMasterView;
        if(toMasterView){
            this.pageView = "To Master Page";
        } else{
            this.pageView = "To Detail View";
        }
    }


    //Getter

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }

    public AllStaffsPagination getPagination() {
        return pagination;
    }

    public void setPagination(AllStaffsPagination pagination) {
        this.pagination = pagination;
    }

    public AllStaffsFilter getFilter() {
        return filter;
    }

    public void setFilter(AllStaffsFilter filter) {
        this.filter = filter;
    }

    public List<Staff> getFilteredStaffs() {
        return filteredStaffs;
    }

    public void setFilteredStaffs(List<Staff> filteredStaffs) {
        this.filteredStaffs = filteredStaffs;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getPageView() {
        return pageView;
    }

    public void setPageView(String pageView) {
        this.pageView = pageView;
    }

    public boolean isToMasterView() {
        return toMasterView;
    }

    public void setToMasterView(boolean toMasterView) {
        this.toMasterView = toMasterView;
    }
}
