package com.ibee.mbordernest.model;

public class TotalListTableModelClass {//NameIdReservedAdapter

    private String id;
    private String table_name;
    private String isopen;
    private Boolean sentToBilling;
    private String orderType;
    private String orderTypeName;
    private String customer;
    private String amount;

    private int orderId;
    private String  posTableId;


    public TotalListTableModelClass() {
    }

    public TotalListTableModelClass(String table_name, String id, String isopen, String orderType, int orderId, Boolean sentToBilling, String orderTypeName, String customer, String amount,String posTableId) {
        this.table_name = table_name;
//        this.total_items = total_items;
//        this.total_items_list = total_items_list;
        this.isopen = isopen;
        this.id = id;
        this.orderType = orderType;
        this.orderTypeName = orderTypeName;
        this.orderId = orderId;
        this.sentToBilling = sentToBilling;
        this.customer = customer;
        this.amount = amount;
        this.posTableId = posTableId;


    }

    public String getPosTableId() {
        return posTableId;
    }

    public void setPosTableId(String posTableId) {
        this.posTableId = posTableId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public Boolean getSentToBilling() {
        return sentToBilling;
    }

    public void setSentToBilling(Boolean sentToBilling) {
        this.sentToBilling = sentToBilling;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getIsopen() {
        return isopen;
    }

    public void setIsopen(String isopen) {
        this.isopen = isopen;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}

