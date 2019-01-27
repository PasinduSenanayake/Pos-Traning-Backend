package com.training.posproject.model;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

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


    private List<OrderItem> orderItems = new ArrayList<>();

    private void setTotal(){
        float total = 0.00f;
        for (OrderItem orderItem:orderItems) {
            total+=orderItem.getQuantity()*orderItem.getUnitPrice();
        }
        this.totalAmount = total;
    }

    public List<OrderItem> getOrderItems() {
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

    public void setOrderItems(List<OrderItem> orderItems) {
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
        this.setTotal();
    }

    public void deleteItemByCode (String code){
        orderItems.removeIf(orderItem -> orderItem.getCode().equals(code));
        this.setTotal();
    }
    public void updateItemByCode(String code, int quantity){
        orderItems.forEach((orderItem -> {
            if (orderItem.getCode().equals(code)) {
                orderItem.setQuantity(quantity);
            }}));
        this.setTotal();
    }

    public void emptyItems (){
        this.orderItems.clear();
    }


}
