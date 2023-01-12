package com.ibee.mbordernest.model;

import java.io.Serializable;

public class SendKotCartItemListModel implements Serializable {

    private int detailId;
    private int pId;
    private String name;
    private int quantity;
    private int oldquantity;
    private Double price;
    private Boolean sentToKOT;
    private String comment;


    public SendKotCartItemListModel() {
    }

    public SendKotCartItemListModel(int detailId, int pId, String name, int quantity, int oldquantity, Double price, Boolean sentToKOT, String comment ) {
        this.detailId = detailId;
        this.pId = pId;
        this.name = name;
        this.quantity = quantity;
        this.oldquantity = oldquantity;

        this.price = price;
        this.comment = comment;
        this.sentToKOT = sentToKOT;
    }

    public int getOldquantity() {
        return oldquantity;
    }

    public void setOldquantity(int oldquantity) {
        this.oldquantity = oldquantity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getSentToKOT() {
        return sentToKOT;
    }

    public void setSentToKOT(Boolean sentToKOT) {
        this.sentToKOT = sentToKOT;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

