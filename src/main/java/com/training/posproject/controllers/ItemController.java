package com.training.posproject.controllers;

import com.training.posproject.service.ItemService;
import com.training.posproject.util.Pair;
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
    //@Qualifier("clientService")
    public void setItemService(ItemService itemService){
        this.itemService=itemService;
    }


    @GetMapping("/all")
    public ResponseEntity getAllItems() {
        try {
            Pair response = itemService.getAllItems();
            if(response.isSuccess()){
                return ResponseEntity.ok(response.getResponse());
            }
            else {
                return ResponseEntity.status(500).body("Server Error");
            }

        }
        catch (Exception e){
             return ResponseEntity.status(500).body("Server Error");
        }
    }
}
