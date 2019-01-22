package com.training.posproject.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class OrderItem {

    @NotBlank
    @Size(min = 10, max = 10)
    private String code;

    @NotBlank
    @Size(min=3, max = 50)
    private String name;

    @NotBlank
    @Size(min=3, max = 50)
    private float unitPrice;

    @NotBlank
    private int quantity;

    public OrderItem(String code, String name, float unitPrice, int quantity){
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.code = code;
        this.name = name;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
