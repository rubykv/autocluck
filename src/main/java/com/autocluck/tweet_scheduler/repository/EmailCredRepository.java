package com.autocluck.tweet_scheduler.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.autocluck.tweet_scheduler.model.EmailCred;

@Repository
public interface EmailCredRepository extends MongoRepository<EmailCred, String> {

}
