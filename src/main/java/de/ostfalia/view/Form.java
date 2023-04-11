package de.ostfalia.view;

public class Form {

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
