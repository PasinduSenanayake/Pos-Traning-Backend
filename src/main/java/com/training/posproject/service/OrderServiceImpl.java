package com.training.posproject.service;

import com.training.posproject.model.Item;
import com.training.posproject.model.Order;
import com.training.posproject.model.OrderItem;
import com.training.posproject.repository.ItemRepository;
import com.training.posproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;


    private String generateOrderId() {
        UUID uuid = UUID.randomUUID();
        String uId = Long.toString(uuid.getMostSignificantBits(), 94) + '-' + Long.toString(uuid.getLeastSignificantBits(), 94);
        uId = uId.replace("-", "A");
        return uId;
    }


    public String createOrder() {

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            Order newOrder = new Order(generateOrderId(), "open", dateFormat.format(date));
            orderRepository.save(newOrder);
            return newOrder.getuId();
    }

    public List<Order> getAllOrders(String state) {

            List<Order> orderList = null;
            switch (state) {
                case "open":
                    orderList = orderRepository.findAllByState("open");
                    orderList.forEach(Order::emptyItems);
                    break;

                case "close":
                    orderList = orderRepository.findAllByState("close");
                    orderList.forEach(Order::emptyItems);
                    break;

                case "all":
                    orderList =  orderRepository.findAll();
                    orderList.forEach(Order::emptyItems);
                    break;

                default: throw new RuntimeException("unreachable");
            }

        return orderList;

    }

    public boolean deleteOrder(String orderId) {

            orderRepository.deleteByUId(orderId);
            return true;

    }

    public  List<OrderItem> updateOrder(String updateType, String orderId, String itemId, String quantity) {
            Order updateOrder = orderRepository.findByUId(orderId);
            switch (updateType) {
                case "add":
                    Item newItem = itemRepository.findByCode(itemId);
                    OrderItem newOrderItem = new OrderItem(newItem.getCode(), newItem.getName(), newItem.getUnitPrice(), Integer.parseInt(quantity));
                    updateOrder.addItems(newOrderItem);
                    orderRepository.save(updateOrder);
                    break;

                case "update":
                    updateOrder.updateItemByCode(itemId, Integer.parseInt(quantity));
                    orderRepository.save(updateOrder);
                    break;


                case "delete":
                    updateOrder.deleteItemByCode(itemId);
                    orderRepository.save(updateOrder);
                    break;
                default: throw new RuntimeException("unreachable");


            }
        return  updateOrder.getOrderItems();
    }

    public  Order getOrder(String orderId) {

            return  orderRepository.findByUId(orderId);
    }
}
