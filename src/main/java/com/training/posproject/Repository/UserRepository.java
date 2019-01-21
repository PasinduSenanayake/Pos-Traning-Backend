package com.training.posproject.Repository;


import java.util.Optional;

import com.training.posproject.Model.User;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Document(collection = "user")
public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
