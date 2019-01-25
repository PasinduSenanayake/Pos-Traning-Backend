package com.training.posproject.repository;

import com.training.posproject.model.Order;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest

public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired

    MongoTemplate mongoTemplate;

    @Before
    public void seedDb() {
        Order newOrder = new Order("12345678900987654321","open","2019/01/24");
        mongoTemplate.save(newOrder, "order");
    }

    @After
    public void resetDb() {
        mongoTemplate.dropCollection("order");
    }

    @Test
    public void getUserByIdTest(){
        Assert.assertNotNull(orderRepository.findByUId("12345678900987654321"));

    }
    @Test
    public void findAllByStateTest(){
        Assert.assertEquals(orderRepository.findAllByState("open").size(),1);

    }
    @Test
    public void deleteByUIdTest(){
        orderRepository.deleteByUId("12345678900987654321");
        Assert.assertNull(orderRepository.findByUId("12345678900987654321"));

    }

}
