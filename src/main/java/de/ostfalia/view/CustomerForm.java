package de.ostfalia.view;

import de.ostfalia.se.boundary.CustomerService;
import de.ostfalia.se.entity.Customer;

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
public class CustomerForm implements Serializable {
    @Inject
    CustomerService cs;
    @NotNull(message = "First Name cannot be empty")
    private String firstname;
    @NotNull(message = "Last Name cannot be empty")
    private String lastname;
    @NotNull(message = "Email cannot be empty")
    private String email;
    @NotNull(message = "Phone cannot be empty")
    private String phone;
    @NotNull(message = "Zip cannot be empty")
    private String zip;
    @NotNull(message = "State cannot be empty")
    private String state;
    @NotNull(message = "Street cannot be empty")
    private String street;
    private String operation; //Can either be Create, Read, Edit or Delete
    private Customer customer;
    private Form form;

    public CustomerForm() {
    }

    @PostConstruct
    public void init(){
        Form form = new Form();
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if(id != null){
            this.customer = cs.findById(Long.valueOf(id));
        } else {
            this.customer = new Customer();
        }
        String operation = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("operation");
        this.operation = form.operationOnForm(operation, "Customer");  //determines operation to be performed
        autoFillForm();
    }


    public void autoFillForm(){
        this.firstname = customer.getFirstname();
        this.lastname = customer.getLastname();
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
        this.zip = customer.getZip();
        this.state = customer.getState();
        this.street = customer.getStreet();
    }

    /**
     * creates a customer and saves to the customers table
     *
     * The user is then redirected to the JSF Page 'allCustomers.xhtml'
     *
     * @return 'allCustomers.xhtml?faces-redirect=true'
     */
    public String submitForm() {
       // cs.save(c);
        return "allCustomers" + "?faces-redirect=true";
    }

    //Getter and Setters
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
}
