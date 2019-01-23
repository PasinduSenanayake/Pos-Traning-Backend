package com.training.posproject.controllers;


import com.training.posproject.model.Item;
import com.training.posproject.model.Order;
import com.training.posproject.model.OrderItem;
import com.training.posproject.repository.ItemRepository;
import com.training.posproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;


    @GetMapping("/create")
    public ResponseEntity createOrder() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            Order newOrder = new Order(generateOrderId(), "open", dateFormat.format(date));
            orderRepository.save(newOrder);
            return ResponseEntity.ok(newOrder.getuId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server Error");
        }


    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<?> updateOrder(@RequestBody Map<String, String> requestObject) {
        try {
            Order updateOrder = orderRepository.findByUId(requestObject.get("orderId"));
            switch (requestObject.get("updateType")) {
                case "add":
                    Item newItem = itemRepository.findByCode(requestObject.get("itemId"));
                    OrderItem newOrderItem = new OrderItem(newItem.getCode(), newItem.getName(), newItem.getUnitPrice(), Integer.parseInt(requestObject.get("quantity")));
                    updateOrder.addItems(newOrderItem);
                    orderRepository.save(updateOrder);
                    return ResponseEntity.ok(updateOrder.getOrderItems());

                case "update":
                    updateOrder.updateItemByCode(requestObject.get("itemId"), Integer.parseInt(requestObject.get("quantity")));
                    orderRepository.save(updateOrder);
                    return ResponseEntity.ok(updateOrder.getOrderItems());

                case "delete":
                    updateOrder.deleteItemByCode(requestObject.get("itemId"));
                    orderRepository.save(updateOrder);
                    return ResponseEntity.ok(updateOrder.getOrderItems());
                default:
                    return ResponseEntity.badRequest().body("Bad Request");

            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server Error");
        }

    }

    @GetMapping("/getOrder")
    public ResponseEntity getOrder(@RequestParam("orderId") String orderId) {
        try {
            return ResponseEntity.ok(orderRepository.findByUId(orderId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server Error");
        }
    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity deleteOrder(@RequestParam("orderId") String orderId) {
        try {
            orderRepository.deleteByUId(orderId);
            return ResponseEntity.ok("deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server Error");
        }
    }

    @GetMapping("/getAllOpenOrders")
    public ResponseEntity<ArrayList<Order>> getAllOpenOrders() {
        ArrayList<Order> orderList = orderRepository.findAllByState("open");
        orderList.forEach(Order::emptyItems);
        return ResponseEntity.ok(orderList);
    }


    private String generateOrderId() {
        UUID uuid = UUID.randomUUID();
        String uId = Long.toString(uuid.getMostSignificantBits(), 94) + '-' + Long.toString(uuid.getLeastSignificantBits(), 94);
        uId = uId.replace("-", "A");
        return uId;
    }
}
