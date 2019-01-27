package com.training.posproject.controllers;

import com.training.posproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/order")
public class OrderController {



    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService){
        this.orderService=orderService;
    }


    @PostMapping("/create")
    public ResponseEntity createOrder() {



        try {

            String newOrderId= orderService.createOrder();
            return ResponseEntity.ok(newOrderId);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }


    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity updateOrder(@RequestBody Map<String, String> requestObject) {

        String updateType= requestObject.get("updateType");
        try {
            if (updateType.equals("delete")) {
                return ResponseEntity.ok(orderService.updateOrder(updateType, requestObject.get("orderId"), requestObject.get("itemId"), "0"));
            } else {
                return ResponseEntity.ok(orderService.updateOrder(updateType, requestObject.get("orderId"), requestObject.get("itemId"), requestObject.get("quantity")));

            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }

    }

    @GetMapping("/getOrder")
    public ResponseEntity getOrder(@RequestParam("orderId") String orderId) {

        try {

                return ResponseEntity.ok(orderService.getOrder(orderId));

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }

    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity deleteOrder(@RequestParam("orderId") String orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok("deleted Successfully");

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getAllOpenOrders")
    public ResponseEntity getAllOpenOrders() {

        try {

                return ResponseEntity.ok(orderService.getAllOrders("open"));

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }

    }


}
