package com.google.userKeep.userKeep.controller;

import com.google.userKeep.userKeep.model.AuthReq;
import com.google.userKeep.userKeep.model.UserModel;
import com.google.userKeep.userKeep.service.JWTService;
import com.google.userKeep.userKeep.service.UserService;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/public/register")
    public ResponseEntity<?> createUser(@RequestBody UserModel userData){
        logger.info("UserController.createUser: Started user creation process");
        UserModel response = userService.createUser(userData);
        logger.info("Finished user creation process");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<?> getAllUsers(){
        logger.info("UserController.getAllUsers: Started retrieval of all users");
        List<UserModel> response = userService.getAllUsers();
        if(response == null){
            logger.warn("Stopped all users retrieval process with error");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Finished retrieval of all users");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/public/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id){
        logger.info("UserController.getUserById: Started retrieval of user by id");
        UserModel response = userService.getUserById(id);
        if(response == null){
            logger.warn("Stopped user retrieval process with error");
            return new ResponseEntity<>("No such users",HttpStatus.NOT_FOUND);
        }
        logger.info("Finished retrieval of user by id");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody  AuthReq authReq){
        logger.info("UserController.authenticateAndGetToken: generating token");
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword()));
        if(authenticate.isAuthenticated()){
            return jwtService.generateToken(authReq.getUsername());
        }else{
            throw new UsernameNotFoundException("Invalid user request.");
        }

    }

}
