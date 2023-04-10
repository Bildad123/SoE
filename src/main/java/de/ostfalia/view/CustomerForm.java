package de.ostfalia.view;

import de.ostfalia.se.boundary.CustomerService;
import de.ostfalia.se.entity.Customer;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;


/**
 * Bean for the JSF Page 'customerForm.xhtml'
 */
@Named
@ViewScoped
public class CustomerForm implements Serializable {

    @Inject
    CustomerService cs;

    private String operation;
    private Customer customer;

    public CustomerForm() {
    }

    @PostConstruct
    public void init(){
        String customerId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        System.out.println("customer id : " + customerId);
        if(customerId != null){
            this.customer = cs.findById(Long.valueOf(customerId));
        } else {

            this.customer = new Customer();
        }
        String operation = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("operation");
        this.operation = operation(operation);

    }

    public String operation(String operation){
        if(operation != null){
            switch (operation){
                case "Edit" :  {
                    return "Edit Customer";
                }
                case "Delete" : {
                    return "Delete Customer";
                }
                case "Read" : {
                    return "Read Customer";
                }
                case "Create" : {
                    return "Create Customer";
                }
            }
        }
        return "Create Customer";  //default
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


}
