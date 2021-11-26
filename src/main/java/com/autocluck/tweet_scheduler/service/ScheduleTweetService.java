package com.autocluck.tweet_scheduler.service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.autocluck.tweet_scheduler.external_client.TwitterClient;
import com.autocluck.tweet_scheduler.model.Tweet;
import com.autocluck.tweet_scheduler.repository.TweetRepository;

@Service
public class ScheduleTweetService {

	@Autowired
	private TweetRepository tweetRepository;

	@Autowired
	private TwitterClient twitterClient;

	@Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
	public void tweet() {
		try {
			List<Tweet> tweets = tweetRepository.findAll();
			Random random = new Random();
			// TODO change logic here
			Tweet selectedTweet = tweets.get(random.nextInt(tweets.size()));
			String tweetToPublish = selectedTweet.getContent();
			twitterClient.doPost(tweetToPublish);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
