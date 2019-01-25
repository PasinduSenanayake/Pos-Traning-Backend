package com.training.posproject.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Document(collection = "item")
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

    public Item(String code, String name, float unitPrice){
        this.code = code;
        this.name =name;
        this.unitPrice = unitPrice;
    }


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
