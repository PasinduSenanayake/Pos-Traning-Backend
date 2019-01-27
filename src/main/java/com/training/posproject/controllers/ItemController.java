package com.training.posproject.controllers;

import com.training.posproject.service.ItemService;
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


    private ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService){
        this.itemService=itemService;
    }


    @GetMapping("/all")
    public ResponseEntity getAllItems() {
        try {

            return ResponseEntity.ok(itemService.getAllItems());

        }
        catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
}
