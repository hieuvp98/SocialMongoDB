package com.teme.spring.repository;

import com.teme.spring.entities.AppUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<AppUser, String> {
    AppUser findById(ObjectId id);
    AppUser getAppUserById(String id);
    AppUser findByIdUser(String id);
    boolean existsUserByIdUser(String id);
}
