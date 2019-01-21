package com.training.posproject.Repository;


import com.training.posproject.Model.Order;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;


@Document(collection = "order")
public interface OrderRepository extends MongoRepository<Order, String> {
    ArrayList<Order> findAllByState(String State);

}

