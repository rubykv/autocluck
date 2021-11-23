package com.autocluck.tweet_scheduler.repository;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.autocluck.tweet_scheduler.model.Tweet;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String>{

	Stream<Tweet> findAllBy();
}
