package com.training.posproject.Repository;


import com.training.posproject.Model.Item;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;


@Document(collection = "item")
public interface ItemRepository extends MongoRepository<Item, String> {


}
