package com.vin.docker.entity;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MyMongoRepository extends MongoRepository<MessageTracker, String>{

}
