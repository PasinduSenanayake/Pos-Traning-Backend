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
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600,allowCredentials="true")
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;


    @GetMapping("/allOpen")
    public ArrayList<Order> getOpenOrders() {
         return orderRepository.findAllByState("open");

    }

    @GetMapping("/create")
    public Order createOrder() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        Order newOrder = new Order(generateOrderId(),"open",dateFormat.format(date));
        orderRepository.save(newOrder);
        return newOrder;

    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<?> updateOrder (@RequestBody Map<String, String> requestObject ) {
        Order updateOrder = orderRepository.findByUId(requestObject.get("orderId"));
        switch (requestObject.get("updateType")){
            case "add":
                Item newItem = itemRepository.findByCode(requestObject.get("itemId"));
                OrderItem newOrderItem = new OrderItem(newItem.getCode(),newItem.getName(),newItem.getUnitPrice(),2);
                updateOrder.addItems(newOrderItem);
                return ResponseEntity.ok(updateOrder.getOrderItems());

            case "update":
                updateOrder.updateItemByCode(requestObject.get("itemId"),Integer.parseInt(requestObject.get("quantity")));
                return ResponseEntity.ok(updateOrder.getOrderItems());

            case "delete":
                updateOrder.deleteItemByCode(requestObject.get("itemId"));
                return ResponseEntity.ok(updateOrder.getOrderItems());
            default:
                return ResponseEntity.badRequest().body("Error");

        }

    }

    @GetMapping("/getOrder")
    public Order getOrder (@RequestParam("orderId") String orderId){
        return orderRepository.findByUId(orderId);
    }


    private String generateOrderId() {
        UUID uuid = UUID.randomUUID();
        String uId = Long.toString(uuid.getMostSignificantBits(), 94) + '-' + Long.toString(uuid.getLeastSignificantBits(), 94);
        uId = uId.replace("-","A");
        return uId;
    }
}
