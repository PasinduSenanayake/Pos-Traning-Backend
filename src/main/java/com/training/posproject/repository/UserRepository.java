package com.training.posproject.repository;


import java.util.Optional;

import com.training.posproject.model.User;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;


@Document(collection = "user")
public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
