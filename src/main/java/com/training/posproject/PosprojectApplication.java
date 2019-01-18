package com.training.posproject;

import com.training.posproject.Model.Role;
import com.training.posproject.Model.User;
import com.training.posproject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication(exclude = { RedisAutoConfiguration.class})
public class PosprojectApplication implements CommandLineRunner {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(PosprojectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        repository.deleteAll();

        // save a couple of Users
        User newUser1 = new User("testUser1", "testU123",passwordEncoder.encode("catWoman"));
        Set<Role> userRoles = new HashSet<Role>();
        userRoles.add(Role.ROLE_ADMIN);
        newUser1.setRoles(userRoles);
        repository.save(newUser1);
        repository.save(new User("testUser2", "testU456",passwordEncoder.encode("catWoman")));

        // fetch all Users
        System.out.println("User found with findAll():");
        System.out.println("-------------------------------");
        for (User user : repository.findAll()) {
            System.out.println(user);
        }
        System.out.println();

        // fetch an individual user
        System.out.println("User found with findByUsername('testU123'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByUsername("testU123"));

    }

}

