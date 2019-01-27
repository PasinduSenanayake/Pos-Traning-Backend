package com.training.posproject.service;

import com.training.posproject.model.Item;
import com.training.posproject.model.Order;
import com.training.posproject.model.OrderItem;
import com.training.posproject.repository.ItemRepository;
import com.training.posproject.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.ArgumentMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class OrderServiceImpTest {
    @TestConfiguration
    static class OrderServiceImplTestContextConfiguration {

        @Bean
        public OrderService setOrderService() {
            return new OrderServiceImpl();
        }
    }

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ItemRepository itemRepository;

    @Before
    public void setUp() {
        Order newOpenOrder = new Order("12345678900987654321","open","2019/01/24");
        Order newCloseOrder = new Order("1234567890098765432A","close","2019/01/24");
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(newOpenOrder);
        orderList.add(newCloseOrder);
        Mockito.when(orderRepository.findAllByState(anyString())).thenReturn(orderList);
        Mockito.when(orderRepository.findAll()).thenReturn(orderList);
        Mockito.when(orderRepository.save( (Order) notNull())).thenReturn(null);
        Mockito.doNothing().when(orderRepository).deleteByUId(anyString());
        Mockito.when(orderRepository.findByUId(anyString())).thenReturn(newOpenOrder);


        Item newItem = new Item("1234567890","testName",10.0f);
        OrderItem existItem = new OrderItem("123456789A","testName",10.0f,2);
        newOpenOrder.addItems(existItem);
        List<Item> itemList = new ArrayList<>();
        itemList.add(newItem);
        Mockito.when(itemRepository.findAll()).thenReturn(itemList);
        Mockito.when(itemRepository.findByCode(anyString())).thenReturn(newItem);

    }

    @Test
    public void getAllOrdersTest() {

        List<Order> orderSet;

        orderSet = orderService.getAllOrders("open");
        Assert.assertTrue(orderSet.stream().anyMatch(o->o.getuId().equals("12345678900987654321")));

        orderSet = orderService.getAllOrders("close");
        Assert.assertTrue(orderSet.stream().anyMatch(o->o.getuId().equals("1234567890098765432A")));

        orderSet = orderService.getAllOrders("all");
        Assert.assertTrue(orderSet.stream().anyMatch(o->o.getuId().equals("1234567890098765432A")));
        Assert.assertTrue(orderSet.stream().anyMatch(o->o.getuId().equals("12345678900987654321")));

    }

    @Test
    public void  createOrderTest(){
        Assert.assertThat(orderService.createOrder(),instanceOf(String.class));
    }

    @Test
    public void  deleteOrderTest(){
        Assert.assertTrue(orderService.deleteOrder("12345678900987654321"));
    }

    @Test
    public void updateOrderAddTest(){
        List<OrderItem> orderItemSet = orderService.updateOrder("add","12345678900987654321","1234567890","3");
        Assert.assertTrue(orderItemSet.stream().anyMatch(o->o.getCode().equals("1234567890")));
    }

    @Test
    public void updateOrderUpdateTest(){
        List<OrderItem> orderItemSet = orderService.updateOrder("update","12345678900987654321","123456789A","10");
        Assert.assertEquals(10,orderItemSet.stream().filter(o->o.getCode().equals("123456789A")).findFirst().get().getQuantity());
    }

    @Test
    public void updateOrderDeleteTest(){
        List<OrderItem> orderItemSet = orderService.updateOrder("delete","12345678900987654321","123456789A","0");
        Assert.assertTrue(orderItemSet.stream().noneMatch(o -> o.getCode().equals("123456789A")));
    }

}
