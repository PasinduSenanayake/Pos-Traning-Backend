package com.training.posproject.Model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

class OrderItem {

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
