package com.training.posproject.controller;

import com.training.posproject.model.Item;
import com.training.posproject.model.Role;
import com.training.posproject.model.User;
import com.training.posproject.repository.ItemRepository;
import com.training.posproject.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest

public class ItemControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;

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

        itemRepository.deleteAll();
        userRepository.deleteAll();
    }

    private String authCookie = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0VTEyMyIsImlhdCI6MTU0ODA4Nzk4MH0.pA-vARXFU-1Q8xLHRxZYyw3WaBYaJczGZFa7mhQE3vKVidDN5lvoCSiwEyEnWrEQmj3RWEEnn92qrNK1ecHavw";


    @Test
    public void createOrderTest() throws Exception {
        Item newItem = new Item("1234567890","testItem",10.0f) ;
        itemRepository.save(newItem);
        String expectedResponse = "[{\"code\":\"1234567890\",\"name\":\"testItem\",\"unitPrice\":10.0}]";
        final Cookie authCookie = new Cookie("posAuthCookie", this.authCookie);
        ResultActions responseAction = mvc.perform(get("/api/item/all").cookie(authCookie));
        String itemsString = responseAction.andReturn().getResponse().getContentAsString();
        Assert.assertEquals(itemsString,expectedResponse);
    }


}
