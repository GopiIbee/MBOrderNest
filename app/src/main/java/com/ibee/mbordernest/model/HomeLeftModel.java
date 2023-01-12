package com.ibee.mbordernest.model;

public class HomeLeftModel {//NameIdModelClassHome

    private String id;
    private String table_name;
    private String count;


    public HomeLeftModel() {
    }

    public HomeLeftModel(String table_name, String id, String count) {
        this.table_name = table_name;
        this.count = count;
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
}

