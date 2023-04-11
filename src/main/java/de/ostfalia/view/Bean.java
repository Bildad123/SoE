package de.ostfalia.view;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

@Named
@ViewScoped
public class Bean implements Serializable {
    private List<Option> options;

    @PostConstruct
    public void init(){
        options = new ArrayList<>();
        options.add(new Option("1", "Option 1"));
        options.add(new Option("2", "Option 2"));
        options.add(new Option("3", "Option 3"));
        options.add(new Option("4", "Option 4"));
        options.add(new Option("5", "Option 5"));

        selectedOption = "";
    }

    private String selectedOption;

    public List<Option> getOptions() {
        return options;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }


}
