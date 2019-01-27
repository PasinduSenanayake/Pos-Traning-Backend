package com.training.posproject.service;

import com.training.posproject.model.Item;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ItemService {
    List<Item> getAllItems();
}
