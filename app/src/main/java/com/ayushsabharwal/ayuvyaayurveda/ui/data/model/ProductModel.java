package com.ayushsabharwal.ayuvyaayurveda.ui.data.model;

import java.io.Serializable;

public class ProductModel implements Serializable {
    private boolean isInCart;
    private int quantity;
    private String description;
    private String imageUrl;
    private String id;
    private String name;
    private double price;

    // Default constructor required for Firebase
    public ProductModel() {
    }

    public ProductModel(String id, String name, double price, String imageUrl, String description, int quantity, boolean isInCart) {
        this.isInCart = isInCart;
        this.quantity = quantity;
        this.description = description;
        this.imageUrl = imageUrl;
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters and setters
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String id) {
        this.imageUrl = id;
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

    public void setPrice(double price) {
        this.price = price;
    }
}
