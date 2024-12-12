package com.ayushsabharwal.ayuvyaayurveda.data.model;

import java.io.Serializable;

public class ProductModel implements Serializable {
    private boolean isInCart;
    private int quantity;
    private String description;
    private String imageUrl;
    private String id;
    private String name;
    private double price;

    public boolean getIsInCart() {
        return isInCart;
    }

    public void setIsInCart(boolean isInCart) {
        this.isInCart = isInCart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
}
