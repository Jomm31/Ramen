package com.example.cce104_ramen;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {
    private final SimpleStringProperty orderName;
    private final SimpleIntegerProperty price;

    public Order(String orderName, int price) {
        this.orderName = new SimpleStringProperty(orderName);
        this.price = new SimpleIntegerProperty(price);
    }

    public String getOrderName() {
        return orderName.get();
    }

    public void setOrderName(String orderName) {
        this.orderName.set(orderName);
    }

    public SimpleStringProperty orderNameProperty() {
        return orderName;
    }

    public int getPrice() {
        return price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }
}
