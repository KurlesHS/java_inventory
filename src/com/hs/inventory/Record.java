package com.hs.inventory;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Record implements Serializable {

    private int sku;
    private int quantity = 1;
    private String name;
    private Date date = new Date();

    int getSku() {
        return sku;
    }

    void setSku(int sku) {
        this.sku = sku;
    }

    void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    void setName(String name) {
        this.name = name;
    }

    void setDate(Date date) {
        this.date = date;
    }

    abstract String getType();

    @Override
    public String toString() {
        String quantityDescription = StringHelpers.getSuffixForNumber(quantity, "штука", "штуки", "штук");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return String.format("%s %d %s %s \"%s\"", dateFormat.format(date),
                quantity, quantityDescription, getType(), name);
    }
}
