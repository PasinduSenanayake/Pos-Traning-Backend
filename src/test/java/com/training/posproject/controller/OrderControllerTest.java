package com.training.posproject.controller;


import com.training.posproject.model.Item;
import com.training.posproject.model.Order;
import com.training.posproject.model.Role;
import com.training.posproject.model.User;
import com.training.posproject.repository.ItemRepository;
import com.training.posproject.repository.OrderRepository;
import com.training.posproject.repository.UserRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import javax.servlet.http.Cookie;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest

public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ItemRepository itemRepository;

    @Before
    public  void setUpAuth() {
        User newUser = new User("testUser1", "testU123",passwordEncoder.encode("catWoman"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(Role.ROLE_ADMIN);
        newUser.setRoles(userRoles);
        userRepository.save(newUser);
    }
    @After
    public void resetDb() {

        orderRepository.deleteAll();
        userRepository.deleteAll();
    }

    private String authCookie = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0VTEyMyIsImlhdCI6MTU0ODA4Nzk4MH0.pA-vARXFU-1Q8xLHRxZYyw3WaBYaJczGZFa7mhQE3vKVidDN5lvoCSiwEyEnWrEQmj3RWEEnn92qrNK1ecHavw";


    @Test
    public void createOrderTest() throws Exception {
        final Cookie authCookie = new Cookie("posAuthCookie", this.authCookie);
        ResultActions responseAction = mvc.perform(post("/api/order/create").cookie(authCookie));
        String orderId = responseAction.andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(orderRepository.findByUId(orderId));
    }

    @Test
    public void getOrderTest() throws Exception {
        String expectedResponse = "{\"uId\":\"12345678900987654321\",\"state\":\"open\",\"createDate\":\"2019/01/24\",\"completedDate\":\"N/A\",\"totalAmount\":0.0,\"orderItems\":[]}";
        Order newOrder = new Order("12345678900987654321","open","2019/01/24");
        orderRepository.save(newOrder);
        final Cookie authCookie = new Cookie("posAuthCookie", this.authCookie);
        ResultActions responseAction = mvc.perform(get("/api/order/getOrder?orderId=12345678900987654321").cookie(authCookie));
        String objectString = responseAction.andReturn().getResponse().getContentAsString();
        Assert.assertEquals(objectString,expectedResponse);

    }

    @Test
    public void updateOrderTest() throws Exception{
        String expectedAddResponse = "[{\"code\":\"1234567890\",\"name\":\"testItem\",\"unitPrice\":10.0,\"quantity\":2}]";
        String  expectedUpdateResponse = "[{\"code\":\"1234567890\",\"name\":\"testItem\",\"unitPrice\":10.0,\"quantity\":4}]";
        String  expectedDeleteResponse = "[]";
        Order newOrder = new Order("12345678900987654321","open","2019/01/24");
        orderRepository.save(newOrder);
        Item newItem = new Item("1234567890","testItem",10.0f);
        itemRepository.save(newItem);

        ResultActions responseAction;
        String objectString;

        final Cookie authCookie = new Cookie("posAuthCookie", this.authCookie);
        responseAction = mvc.perform(put("/api/order/update").cookie(authCookie).contentType("application/json").content("{\"updateType\":\"add\",\"orderId\":\"12345678900987654321\",\"itemId\":\"1234567890\",\"quantity\":\"2\"}"));
        objectString = responseAction.andReturn().getResponse().getContentAsString();
        Assert.assertEquals(expectedAddResponse,objectString);


        responseAction = mvc.perform(put("/api/order/update").cookie(authCookie).contentType("application/json").content("{\"updateType\":\"update\",\"orderId\":\"12345678900987654321\",\"itemId\":\"1234567890\",\"quantity\":\"4\"}"));
        objectString = responseAction.andReturn().getResponse().getContentAsString();
        Assert.assertEquals(expectedUpdateResponse,objectString);

        responseAction = mvc.perform(put("/api/order/update").cookie(authCookie).contentType("application/json").content("{\"updateType\":\"delete\",\"orderId\":\"12345678900987654321\",\"itemId\":\"1234567890\"}"));
        objectString = responseAction.andReturn().getResponse().getContentAsString();
        Assert.assertEquals(expectedDeleteResponse,objectString);

    }

    @Test
    public void deleteOrderTest() throws Exception {
        Order newOrder = new Order("12345678900987654321","open","2019/01/24");
        orderRepository.save(newOrder);
        final Cookie authCookie = new Cookie("posAuthCookie", this.authCookie);
        mvc.perform(delete("/api/order/deleteOrder?orderId=12345678900987654321").cookie(authCookie));
        Assert.assertNull(orderRepository.findByUId("12345678900987654321"));
    }


}
