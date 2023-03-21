package de.ostfalia.view;

import de.ostfalia.se.boundary.CustomerService;
import de.ostfalia.se.entity.Customer;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

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

    @NotNull(message = "Phone cannot be empty")
    String phone;

    @NotNull(message = "Zip cannot be empty")
    String zip;

    @NotNull(message = "Street cannot be empty")
    String street;


    @NotNull(message = "Country cannot be empty")
    String country;



    public CustomerForm() {
    }



    public String submitForm() {
        Customer c = new Customer(
                this.firstName,
                this.lastName,
                this.email,
                this.phone,
                this.zip,
                this.street,
                this.country
        );
        cs.save(c);
        //System.out.println("Customer to be saved : " + c);
        return "allCustomers" + "?faces-redirect=true";
    }


    //Getter and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
