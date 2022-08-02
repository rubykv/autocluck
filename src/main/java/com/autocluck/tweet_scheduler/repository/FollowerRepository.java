package com.autocluck.tweet_scheduler.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.autocluck.tweet_scheduler.model.Follower;

@Repository
public interface FollowerRepository extends MongoRepository<Follower, String>{

}
