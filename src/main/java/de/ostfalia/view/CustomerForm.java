package de.ostfalia.view;

import de.ostfalia.se.boundary.CustomerService;
import de.ostfalia.se.entity.Customer;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    @NotNull(message = "Birthdate cannot be empty")
    String birthdate;

    @NotNull(message = "Zip cannot be empty")
    String zip;

    @NotNull(message = "Street cannot be empty")
    String street;


    public CustomerForm() {
    }

    /**
     * creates a customer and saves to the customers table
     *
     * The user is then redirected to the JSF Page 'allCustomers.xhtml'
     *
     * @return 'allCustomers.xhtml?faces-redirect=true'
     */
//    public String submitForm() {
//        Customer c = new Customer(
//                this.firstName,
//                this.lastName,
//                this.email,
//                toLocalDate(this.birthdate),
//                this.zip,
//                this.street
//        );
//        cs.save(c);
//        return "allCustomers" + "?faces-redirect=true";
//    }


    /**
     * Helper Method to convert string to LocalDate
     * @param dateString
     * @return LocalDate
     */
    public LocalDate toLocalDate(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date;

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

    public String getBirthdate() {
        return birthdate;
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

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
