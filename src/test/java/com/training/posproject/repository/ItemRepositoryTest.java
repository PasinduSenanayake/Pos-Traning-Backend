package com.training.posproject.repository;

import com.training.posproject.model.Item;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest

public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired

    MongoTemplate mongoTemplate;

    @Before
    public void seedDb() {
        Item newItem = new Item("1234567890","testName",10.0f);
        mongoTemplate.save(newItem, "item");
    }

    @After
    public void resetDb() {
        mongoTemplate.dropCollection("item");
    }

    @Test
    public void findByCodeTest(){
        Assert.assertNotNull(itemRepository.findByCode("1234567890"));

    }
}
