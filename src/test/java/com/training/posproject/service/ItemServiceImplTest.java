package com.training.posproject.service;

import com.training.posproject.model.Item;
import com.training.posproject.repository.ItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class ItemServiceImplTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public ItemService setItemService() {
            return new ItemServiceImpl();
        }
    }

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    @Before
    public void setUp() {
        Item newItem = new Item("1234567890","testName",10.0f);
        List<Item> itemList = new ArrayList<>();
        itemList.add(newItem);
        Mockito.when(itemRepository.findAll()).thenReturn(itemList);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getAllItemsTest() {
        Assert.assertTrue(itemService.getAllItems().isSuccess());
        ArrayList<Item> itemSet = (ArrayList<Item>) itemService.getAllItems().getResponse();
        Assert.assertEquals(itemSet.size(),1);
    }
}
