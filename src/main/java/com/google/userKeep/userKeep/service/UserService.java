package com.google.userKeep.userKeep.service;


import com.google.userKeep.userKeep.model.FolderModel;
import com.google.userKeep.userKeep.model.UserModel;
import com.google.userKeep.userKeep.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    WebClient.Builder webCilent;

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserService.class);



    public UserModel createUser(UserModel userData) {
        logger.info("Creating user");
        userData.setFolders(Collections.emptyList());
        logger.info("created user successfully");
        return userRepository.save(userData);
    }

    public List<UserModel> getAllUsers() {
        logger.info("Getting all users");
        List<UserModel> users = userRepository.findAll();

        users.stream().map(user->{
            ResponseEntity<List> folders = webCilent.build()
                    .get()
                    .uri("http://localhost:3000/folders/user/"+user.getId())
                    .retrieve()
                    .toEntity(List.class)
                    .block();
            user.setFolders(folders.getBody());
            return user;
        }).collect(Collectors.toList());

        if(users.size()==0) {
            logger.warn("No users found");
            return null;
        }
        logger.info("users found");
        return users;
    }

    public UserModel getUserById(String id) {
        logger.info("Getting user by id");
        Optional<UserModel> userOptional = userRepository.findById(id);

        if(userOptional.isPresent()) {
            logger.info("user found");
            UserModel user = userOptional.get();
            logger.info("getting folders for user");
            ResponseEntity<List> folders = webCilent.build()
                    .get()
                    .uri("http://localhost:3000/folders/user/"+id)
                    .retrieve()
                    .toEntity(List.class)
                    .block();
            logger.info("folders found for the user");
            user.setFolders(folders.getBody());
            return user;
        }
        logger.warn("user not found");
        return null;
    }
}
