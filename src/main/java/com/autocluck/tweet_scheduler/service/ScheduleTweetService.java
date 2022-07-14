package com.autocluck.tweet_scheduler.service;

import java.util.List;
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

	@Autowired
	private EmailService emailService;

	@Scheduled(fixedRate = 3, timeUnit = TimeUnit.HOURS)
	public void tweet() {
		try {
			Tweet toTweet = tweetRepository.findTopByOrderByDateAsc();
			postTweet(toTweet);
		} catch (Exception ex) {
			logger.error("Couldn't update tweet ", ex);
		}
	}

	private void postTweet(Tweet selectedTweet) throws Exception {

		if (null != selectedTweet) {
			String tweetToPublish = selectedTweet.getContent();
			String toDeleteByName = selectedTweet.getName();
			List<Identity> identities = identityRepository.findAll();
			twitterClient.doPost(tweetToPublish, identities.get(0));
			tweetRepository.deleteTweetByName(toDeleteByName);
		} else {
			logger.info("No More Tweets To Post");
			try {
				emailService.sendMail();
			} catch (Exception ex) {
				logger.error("Notification not sent");
			}
		}
	}
}
