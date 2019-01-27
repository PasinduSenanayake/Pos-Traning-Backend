package com.training.posproject.service;

import com.training.posproject.model.Item;
import com.training.posproject.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;


    @Transactional
    public List<Item> getAllItems() {

            return itemRepository.findAll();


    }
}
