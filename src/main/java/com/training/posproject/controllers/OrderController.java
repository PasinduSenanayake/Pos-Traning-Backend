package com.training.posproject.controllers;

import com.training.posproject.service.OrderService;
import com.training.posproject.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity createOrder() {

        try {
            Pair response = orderService.createOrder();
            if(response.isSuccess()){
                return ResponseEntity.ok(response.getResponse());
            } else {
                return ResponseEntity.status(500).body("Server Error");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server Error");
        }


    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<?> updateOrder(@RequestBody Map<String, String> requestObject) {


        try {
            Pair response ;
            if (requestObject.get("updateType").equals("delete")) {
                response =  orderService.updateOrder(requestObject.get("updateType"), requestObject.get("orderId"), requestObject.get("itemId"), "0");
            } else {
                response = orderService.updateOrder(requestObject.get("updateType"), requestObject.get("orderId"), requestObject.get("itemId"), requestObject.get("quantity"));

            }
            if(response.isSuccess()){
                return ResponseEntity.ok(response.getResponse());
            } else {
                return ResponseEntity.status(500).body("Server Error");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server Error");
        }

    }

    @GetMapping("/getOrder")
    public ResponseEntity getOrder(@RequestParam("orderId") String orderId) {

        try {
            Pair response = orderService.getOrder(orderId);
            if(response.isSuccess()){
                return ResponseEntity.ok(response.getResponse());
            } else {
                return ResponseEntity.status(500).body("Server Error");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server Error");
        }

    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity deleteOrder(@RequestParam("orderId") String orderId) {
        try {
            if (orderService.deleteOrder(orderId)) {
                return ResponseEntity.ok("deleted Successfully");
            } else {
                return ResponseEntity.status(500).body("Server Error");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server Error");
        }
    }

    @GetMapping("/getAllOpenOrders")
    public ResponseEntity getAllOpenOrders() {

        try {
            Pair response = orderService.getAllOrders("open");
            if (response.isSuccess()) {
                return ResponseEntity.ok(response.getResponse());
            } else {
                return ResponseEntity.status(500).body("Server Error");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server Error");
        }

    }


}
