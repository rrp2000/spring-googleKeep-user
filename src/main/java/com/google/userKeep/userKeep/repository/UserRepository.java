package com.google.userKeep.userKeep.repository;

import com.google.userKeep.userKeep.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserModel,String> {
}
