package de.ostfalia.se.form;

/**
 * Class determines the operation to be performed on the form
 *
 * Class is used by the following bean classes :
 * 1) OrderForm
 * 2) ProductForm
 * 3) CustomerForm
 */
public class Form {

    private Operation operation;

    public String operationOnForm(String operationName, String formName){
        if(operationName != null && !operationName.isBlank()){
            this.operation = Operation.valueOf(operationName);
        } else{
            this.operation = Operation.valueOf("Create");
        }
        return this.operation.toString() + " " + formName;

    }
}
