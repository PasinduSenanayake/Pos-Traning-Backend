package com.training.posproject.controllers;


import com.training.posproject.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity getOpenOrders() {
        try {
            return ResponseEntity.ok(itemRepository.findAll());
        }
        catch (Exception e){
             return ResponseEntity.status(500).body("Server Error");
        }
    }
}
