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
    @Size(min = 10, max = 10)
    private String uId;

    @NotBlank
    @Size(min=3, max = 50)
    private String state;

    @NotBlank
    private Date createDate;

    private Date completedDate;

    @NotBlank
    private float totalAmount;


    private Set<OrderItem> orderItems = new HashSet<>();

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public String getState() {
        return state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public void setCreateDate(Date createDate) {
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

    public void deleteItem (OrderItem item){
        orderItems.remove(item);
    }
}
