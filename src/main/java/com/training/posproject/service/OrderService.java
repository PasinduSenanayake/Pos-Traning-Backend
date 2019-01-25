package com.training.posproject.service;

import com.training.posproject.model.Item;
import com.training.posproject.model.Order;
import com.training.posproject.model.OrderItem;
import com.training.posproject.repository.ItemRepository;
import com.training.posproject.repository.OrderRepository;
import com.training.posproject.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


@Service
public class OrderService {
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


    public Pair<Boolean,?> createOrder() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            Order newOrder = new Order(generateOrderId(), "open", dateFormat.format(date));
            orderRepository.save(newOrder);
            return new Pair<>(true,newOrder.getuId());
        } catch (Exception e) {
            return new Pair<>(false,null);

        }


    }

    public Pair<Boolean,?> getAllOrders(String  state) {

        try {

            ArrayList<Order> orderList = null;
            switch (state) {
                case "open":
                    orderList = orderRepository.findAllByState("open");
                    break;

                case "close":
                    orderList = orderRepository.findAllByState("close");
                    break;

                case "all":
                    orderList = (ArrayList<Order>) orderRepository.findAll();
                    break;
            }

            orderList.forEach(Order::emptyItems);


            return new Pair<>(true,orderList);
        }
        catch (Exception e){
            return new Pair<>(false,null);

        }

    }

    public boolean deleteOrder( String orderId) {
        try {
            orderRepository.deleteByUId(orderId);
            return true;

        } catch (Exception e) {
           return false;
        }
    }

    public Pair<Boolean,?>  updateOrder(String updateType, String orderId, String itemId, String quantity ) {
        try {
            Order updateOrder = orderRepository.findByUId(orderId);
            switch (updateType) {
                case "add":
                    Item newItem = itemRepository.findByCode(itemId);
                    OrderItem newOrderItem = new OrderItem(newItem.getCode(), newItem.getName(), newItem.getUnitPrice(), Integer.parseInt(quantity));
                    updateOrder.addItems(newOrderItem);
                    orderRepository.save(updateOrder);
                    return new Pair<>(true,updateOrder.getOrderItems());

                case "update":
                    updateOrder.updateItemByCode(itemId, Integer.parseInt(quantity));
                    orderRepository.save(updateOrder);
                    return new Pair<>(true,updateOrder.getOrderItems());

                case "delete":
                    updateOrder.deleteItemByCode(itemId);
                    orderRepository.save(updateOrder);
                    return new Pair<>(true,updateOrder.getOrderItems());

            }
            return new Pair<>(false,null);
        } catch (Exception e) {
            return new Pair<>(false,null);

        }
    }

    public Pair<Boolean,?>  getOrder( String orderId) {

        try {
            return new Pair<>(true,orderRepository.findByUId(orderId));

        } catch (Exception e) {
            return new Pair<>(false,null);

        }
    }

}
