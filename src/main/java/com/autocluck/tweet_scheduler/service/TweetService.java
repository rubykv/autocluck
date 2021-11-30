package com.autocluck.tweet_scheduler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autocluck.tweet_scheduler.model.CreateTweetRequest;
import com.autocluck.tweet_scheduler.model.Tweet;
import com.autocluck.tweet_scheduler.repository.TweetRepository;

@Service
public class TweetService {
    Logger logger = LoggerFactory.getLogger(TweetService.class);

	@Autowired
	private TweetRepository tweetRepository;

	public void saveTweet(CreateTweetRequest request) {
		Tweet tweet = new Tweet();
		tweet.setName(request.getName());
		tweet.setContent(request.getContent());
		tweetRepository.save(tweet);
	}
	
	public void deleteTweet(String name) {
		tweetRepository.deleteTweetByName(name);
	}
}
