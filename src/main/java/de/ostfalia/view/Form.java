package de.ostfalia.view;

/**
 * Class determines the operation to be performed on the form
 *
 * Class is used by the following bean classes :
 * 1) OrderForm
 * 2) ProductForm
 * 3) CustomerForm
 */
public class Form {

    /**
     * Method returns the operation to be performed on a given form
     *
     * The default operation is Create
     *
     * @param operation
     * @param formName
     * @return 'Edit/Delete/Read/Create'
     */
    public String operation(String operation, String formName){
        if(operation != null){
            switch (operation){
                case "Edit" :  {
                    return "Edit " + formName;
                }
                case "Delete" : {
                    return "Delete " + formName;
                }
                case "Read" : {
                    return "Read " + formName;
                }
                case "Create" : {
                    return "Create " + formName;
                }
            }
        }
        return "Create " + formName;  //default
    }
}
