package com.ibee.mbordernest.model;

public class ProductsNameIdModelClass {

    private int pId;
    private float price;
    private float taxPer;
    private String name;
    private int categoryId;
    private String addOns;
    private String inventory;
    private String avlqty;

    public ProductsNameIdModelClass(int pId, float price, float taxPer, String name,
                                    int categoryId, String addOns, String inventory, String avlqty) {
        this.pId = pId;
        this.price = price;
        this.taxPer = taxPer;
        this.name = name;
        this.categoryId = categoryId;
        this.addOns = addOns;
        this.inventory = inventory;
        this.avlqty = avlqty;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getAvlqty() {
        return avlqty;
    }

    public void setAvlqty(String avlqty) {
        this.avlqty = avlqty;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTaxPer() {
        return taxPer;
    }

    public void setTaxPer(float taxPer) {
        this.taxPer = taxPer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getAddOns() {
        return addOns;
    }

    public void setAddOns(String addOns) {
        this.addOns = addOns;
    }
}

