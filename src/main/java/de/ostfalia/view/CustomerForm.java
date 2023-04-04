package de.ostfalia.view;

import de.ostfalia.se.boundary.CustomerService;
import de.ostfalia.se.entity.Customer;

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

    @NotNull(message = "Firstname cannot be empty")
    String firstName;

    @NotNull(message = "Lastname cannot be empty")
    String lastName;

    @NotNull(message = "Email cannot be empty")
    String email;

    @NotNull(message = "Phone cannot be null")
    String phone;

    @NotNull(message = "Zip cannot be empty")
    String zip;

    @NotNull(message = "State cannot be null")
    String state;

    @NotNull(message = "Street cannot be empty")
    String street;

    private String formType;




    public CustomerForm() {
    }

    /**
     * creates a customer and saves to the customers table
     *
     * The user is then redirected to the JSF Page 'allCustomers.xhtml'
     *
     * @return 'allCustomers.xhtml?faces-redirect=true'
     */
    public String submitForm() {
        Customer c = new Customer(
                this.firstName,
                this.lastName,
                this.email,
                this.zip,
                this.street
        );
       // cs.save(c);
        return "allCustomers" + "?faces-redirect=true";
    }


    public String createCustomer(){
        this.formType = "Create Customer";
        return "customerForm.xhtml" + "?faces-redirect=true";
    }

    public String editCustomer(Customer customer){
        this.firstName = customer.getFirstname();
        this.lastName = customer.getLastname();
        this.phone = customer.getPhone();
        this.email = customer.getEmail();
        this.street = customer.getStreet();
        this.state = customer.getState();
        this.formType = "Edit Customer";
        return "allCustomers" + "?faces-redirect=true";
    }



    //Getter and Setters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }


    public String getZip() {
        return zip;
    }

    public String getStreet() {
        return street;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }
}
