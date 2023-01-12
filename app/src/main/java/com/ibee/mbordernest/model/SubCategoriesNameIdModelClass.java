package com.ibee.mbordernest.model;

public class SubCategoriesNameIdModelClass {

    private int id;
    private String table_name;



    public SubCategoriesNameIdModelClass(String table_name, int id) {
        this.table_name = table_name;

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
}

