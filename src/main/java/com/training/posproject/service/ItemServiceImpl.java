package com.training.posproject.service;

import com.training.posproject.repository.ItemRepository;
import com.training.posproject.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;


    @Transactional
    public Pair<Boolean,?> getAllItems() {

        try {
            return new Pair<>(true,itemRepository.findAll());
        }
        catch (Exception e){
            return new Pair<>(false,null);
        }
    }
}
