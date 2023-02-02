package com.google.userKeep.userKeep.repository;

import com.google.userKeep.userKeep.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserModel,String> {
    @Query
    Optional<UserModel> findByName(String name);
}
