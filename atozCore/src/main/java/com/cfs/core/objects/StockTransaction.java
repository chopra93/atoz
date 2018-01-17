package com.cfs.core.objects;

import java.util.Date;

/**
 * @author chopra
 * 10/01/18
 */
public class StockTransaction {

    String name;
    float price;
    Date when;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    @Override
    public String toString() {
        return "StockTransaction{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", when=" + when +
                '}';
    }
}
