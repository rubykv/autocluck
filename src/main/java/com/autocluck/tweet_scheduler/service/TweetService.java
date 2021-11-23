package com.autocluck.tweet_scheduler.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autocluck.tweet_scheduler.model.CreateTweetRequest;
import com.autocluck.tweet_scheduler.model.Tweet;
import com.autocluck.tweet_scheduler.repository.TweetRepository;

@Service
public class TweetService {

	@Autowired
	private TweetRepository tweetRepository;

	public void saveTweet(CreateTweetRequest request) {
		Tweet tweet = new Tweet();
		tweet.setId(BigInteger.valueOf(request.getContent().hashCode()));
		tweet.setTweet(request.getContent());
		tweetRepository.save(tweet);
	}
}
