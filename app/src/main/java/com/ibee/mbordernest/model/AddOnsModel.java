package com.ibee.mbordernest.model;

import java.io.Serializable;

public class AddOnsModel implements Serializable {

    private String addOnId;
    private String name;
    private String price;



    public AddOnsModel(String addOnId, String name, String price) {
        this.addOnId = addOnId;
        this.name = name;
        this.price = price;
    }

    public String getAddOnId() {
        return addOnId;
    }

    public void setAddOnId(String addOnId) {
        this.addOnId = addOnId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

