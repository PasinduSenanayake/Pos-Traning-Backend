package com.training.posproject.service;

import com.training.posproject.util.Pair;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Pair<Boolean,?> createOrder ();
    Pair<Boolean,?> getAllOrders (String  state);
    boolean deleteOrder( String orderId);
    Pair<Boolean,?>  updateOrder(String updateType, String orderId, String itemId, String quantity );
    Pair<Boolean,?>  getOrder( String orderId);
}


