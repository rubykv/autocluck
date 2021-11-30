package com.autocluck.tweet_scheduler.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.autocluck.tweet_scheduler.external_client.TwitterClient;
import com.autocluck.tweet_scheduler.model.Identity;
import com.autocluck.tweet_scheduler.model.Tweet;
import com.autocluck.tweet_scheduler.repository.IdentityRepository;
import com.autocluck.tweet_scheduler.repository.TweetRepository;

import twitter4j.TwitterException;

@Service
public class ScheduleTweetService {
    Logger logger = LoggerFactory.getLogger(ScheduleTweetService.class);

	@Autowired
	private TweetRepository tweetRepository;
	
	@Autowired
	private IdentityRepository identityRepository;

	@Autowired
	private TwitterClient twitterClient;
	
	private List<String> publishedTweets;

	@Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
	public void tweet() {
		try {
			//fetch tweets from DB
			List<Tweet> tweets = tweetRepository.findAll();
			
			//First time execution OR All tweets have been published at least once, empty the temp list
			if(CollectionUtils.isEmpty(publishedTweets) || publishedTweets.size() == tweets.size()) {
				publishedTweets = new ArrayList<>();
			}
			postTweetIfNotAlreadyPublished(tweets);
		} catch (Exception ex) {
			logger.error("Couldn't update tweet ", ex);
		}
	}

	private void postTweetIfNotAlreadyPublished(List<Tweet> tweets) throws InterruptedException, ExecutionException, IOException, TwitterException {
		Random random = new Random();
		Tweet selectedTweet = tweets.get(random.nextInt(tweets.size()));
		if(!publishedTweets.contains(selectedTweet.getName())) {
			publishedTweets.add(selectedTweet.getName());
			//Post tweet
			String tweetToPublish = selectedTweet.getContent();
			List<Identity> identities = identityRepository.findAll();
			twitterClient.doPost(tweetToPublish, identities.get(0));
		} else {
			postTweetIfNotAlreadyPublished(tweets);
		}
	}
}
