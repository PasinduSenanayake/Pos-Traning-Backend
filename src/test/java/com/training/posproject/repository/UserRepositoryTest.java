package com.training.posproject.repository;

import com.training.posproject.model.User;
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

public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired

    MongoTemplate mongoTemplate;

    @Before
    public void seedDb() {
        User newUser = new User("testUser","testUserName","123456789");
        mongoTemplate.save(newUser, "user");
    }

    @After
    public void resetDb() {
        mongoTemplate.dropCollection("user");
    }

    @Test
    public void findByUserNameTest(){
        Assert.assertNotNull(userRepository.findByUsername("testUserName"));

    }
}
