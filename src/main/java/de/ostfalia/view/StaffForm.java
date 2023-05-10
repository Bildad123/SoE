package de.ostfalia.view;

import de.ostfalia.se.boundary.StaffService;
import de.ostfalia.se.entity.Staff;
import de.ostfalia.se.form.Form;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * Bean for the JSF Page 'customerForm.xhtml'
 */
@Named
@ViewScoped
public class StaffForm implements Serializable {
    @Inject
    StaffService sts;
    @NotNull(message = "First Name cannot be empty")
    private String firstname;
    @NotNull(message = "Last Name cannot be empty")
    private String lastname;
    @NotNull(message = "Email cannot be empty")
    private String email;
    @NotNull(message = "Phone cannot be empty")
    private String phone;
    @NotNull(message = "Active cannot be empty")
    private Integer active;
    private String operation; //Can either be Create, Read, Edit or Delete
    private Staff staff;
    private Form form;

    public StaffForm() {
    }

    @PostConstruct
    public void init(){
        Form form = new Form();
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if(id != null){
            this.staff = sts.findById(Integer.valueOf(id));
        } else {
            this.staff = new Staff();
        }
        String operation = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("operation");
        this.operation = form.operationOnForm(operation, "Staff");  //determines operation to be performed
        if (!this.operation.equals("Create Staff")) {
            autoFillForm();
        }
    }

    public void autoFillForm(){
        this.firstname = staff.getFirstName();
        this.lastname = staff.getLastName();
        this.email = staff.getEmail();
        this.phone = staff.getPhone();
        this.active = staff.getActive();
    }

    public void fillStaff() {
        staff.setFirstName(firstname);
        staff.setLastName(lastname);
        staff.setEmail(email);
        staff.setPhone(phone);
        staff.setActive(active);
    }

    /**
     * creates a staff and saves to the staff table
     *
     * The user is then redirected to the JSF Page 'allStaff.xhtml'
     *
     * @return 'allStaff.xhtml?faces-redirect=true'
     */
    public String submitForm() {
        if (operation.equals("Create Staff")) {
            fillStaff();
            sts.save(staff);
        }
        if (operation.equals("Delete Staff")) {
            sts.delete(staff);
        }
        if (operation.equals("Edit Staff")) {
            fillStaff();
            sts.update(staff);
        }

        return "allStaffs" + "?faces-redirect=true";
    }

    //Getter and Setter

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
}
