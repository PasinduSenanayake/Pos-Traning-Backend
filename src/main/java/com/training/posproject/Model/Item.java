package com.training.posproject.Model;

import org.springframework.data.annotation.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Item {
    @Id
    private String id;

    @NotBlank
    @Size(min = 10, max = 10)
    private String code;

    @NotBlank
    @Size(min=3, max = 30)
    private String name;

    private float unitPrice;


    public float getUnitPrice() {
        return unitPrice;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setName(String name) {
        this.name = name;
    }
}
