package com.training.posproject.Controllers;


import com.training.posproject.Model.Item;
import com.training.posproject.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600,allowCredentials="true")
@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;


    @GetMapping("/all")
    public Iterable<Item> getOpenOrders() {
        return itemRepository.findAll();

    }
}
