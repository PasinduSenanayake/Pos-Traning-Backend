package com.training.posproject.repository;


import com.training.posproject.model.Order;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;


@Document(collection = "order")
public interface OrderRepository extends MongoRepository<Order, String> {
    ArrayList<Order> findAllByState(String State);
    Order findByUId(String uID);
    void deleteByUId(String uID);

}

