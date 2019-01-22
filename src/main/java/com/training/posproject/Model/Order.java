package com.training.posproject.Model;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order {
    @Id
    private String id;

    @NotBlank
    @Size(min = 20, max = 20)
    private String uId;

    @NotBlank
    @Size(min=3, max = 50)
    private String state;

    @NotBlank
    private String createDate;

    @NotBlank
    private String completedDate;

    @NotBlank
    private float totalAmount;

    public Order(String uId, String state, String createDate){
        this.uId = uId;
        this.state = state;
        this.createDate = createDate;
        this.completedDate ="N/A";
        this.totalAmount = 0.00f;
    }


    private Set<OrderItem> orderItems = new HashSet<>();

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public String getState() {
        return state;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public String getuId() {
        return uId;
    }

    public void addItems(OrderItem item){
        orderItems.add(item);
    }

    public void deleteItemByCode (String code){
        orderItems.removeIf(orderItem -> orderItem.getCode().equals(code));
    }
    public void updateItemByCode(String code, int quantity){
        orderItems.forEach((orderItem -> {
            if (orderItem.getCode().equals(code)) {
                orderItem.setQuantity(quantity);
            }}));
    }

}
