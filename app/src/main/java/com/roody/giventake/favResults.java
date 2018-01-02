package com.roody.giventake;

public class favResults {

    private int id;
    private String favor = "";
    private String date = "";
    private String name = "";

    public void setId(int id){this.id = id;}

    public int getId(){return id;}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFavor(String favor) {
        this.favor = favor;
    }

    public String getFavor() {
        return favor;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
