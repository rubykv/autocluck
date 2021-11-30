package com.autocluck.tweet_scheduler.service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.autocluck.tweet_scheduler.external_client.TwitterClient;
import com.autocluck.tweet_scheduler.model.Identity;
import com.autocluck.tweet_scheduler.model.Tweet;
import com.autocluck.tweet_scheduler.repository.IdentityRepository;
import com.autocluck.tweet_scheduler.repository.TweetRepository;

@Service
public class ScheduleTweetService {
    Logger logger = LoggerFactory.getLogger(ScheduleTweetService.class);

	@Autowired
	private TweetRepository tweetRepository;
	
	@Autowired
	private IdentityRepository identityRepository;

	@Autowired
	private TwitterClient twitterClient;

	@Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
	public void tweet() {
		try {
			List<Tweet> tweets = tweetRepository.findAll();
			List<Identity> identities = identityRepository.findAll();
			Random random = new Random();
			// TODO change logic here
			Tweet selectedTweet = tweets.get(random.nextInt(tweets.size()));
			String tweetToPublish = selectedTweet.getContent();
			twitterClient.doPost(tweetToPublish, identities.get(0));
		} catch (Exception ex) {
			logger.error("Couldn't update tweet ", ex);
		}
	}
}
