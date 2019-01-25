package com.training.posproject.repository;


import com.training.posproject.model.Item;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Document(collection = "item")
public interface ItemRepository extends MongoRepository<Item, String> {
    Item findByCode(String code);
}
