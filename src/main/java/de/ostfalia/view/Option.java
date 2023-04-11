package de.ostfalia.view;

public class Option {
    String num;
    String option;



    public Option(String num, String option) {
        this.num = num;
        this.option = option;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}

