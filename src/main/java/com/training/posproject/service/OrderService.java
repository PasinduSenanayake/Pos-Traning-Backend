package com.training.posproject.service;

import com.training.posproject.model.Order;
import com.training.posproject.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    String createOrder ();
    List<Order> getAllOrders (String  state);
    boolean deleteOrder( String orderId);
    List<OrderItem> updateOrder(String updateType, String orderId, String itemId, String quantity );
    Order getOrder( String orderId);
}


