package com.training.posproject.Controllers;


import com.training.posproject.Model.Order;
import com.training.posproject.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600,allowCredentials="true")
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;


    @GetMapping("/allOpenOrders")
    public ArrayList<Order> getOpenOrders() {
         return orderRepository.findAllByState("open");

    }
}
