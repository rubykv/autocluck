package com.autocluck.tweet_scheduler.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.autocluck.tweet_scheduler.model.Identity;

@Repository
public interface IdentityRepository extends MongoRepository<Identity, String>{
	public Identity findByAccount();
}
