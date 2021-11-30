package com.autocluck.tweet_scheduler.external_client;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.autocluck.tweet_scheduler.model.Identity;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

@Component
public class TwitterClient {
    Logger logger = LoggerFactory.getLogger(TwitterClient.class);
	
	public void doPost(String updateStatus,Identity identity) throws InterruptedException, ExecutionException, IOException, TwitterException {
		try {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(identity.getKey(), identity.getSecret());
			AccessToken accessToken = new AccessToken(identity.getAccesstoken(), identity.getTokensecret());
			twitter.setOAuthAccessToken(accessToken);
			twitter.updateStatus(updateStatus);
			logger.info("Successfully updated the status in Twitter.");
		} catch (TwitterException te) {
			logger.error("Error occurred while updating tweet " + te);
			throw te;
		}
	}
}
