package com.autocluck.tweet_scheduler.external_client;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autocluck.tweet_scheduler.config.TweetSchedulerConfig;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

@Component
public class TwitterClient {

	@Autowired
	private TweetSchedulerConfig tweetSchedulerConfig;

	public boolean doPost(String updateStatus) throws InterruptedException, ExecutionException, IOException {
		try {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(tweetSchedulerConfig.getKey(), tweetSchedulerConfig.getSecret());
			AccessToken accessToken = new AccessToken(tweetSchedulerConfig.getAccesstoken(),
					tweetSchedulerConfig.getTokensecret());
			twitter.setOAuthAccessToken(accessToken);
			twitter.updateStatus(updateStatus);
			// TODO Implement logger
			System.out.println("Successfully updated the status in Twitter.");
			return true;
		} catch (TwitterException te) {
			te.printStackTrace();//TODO print to logs
			return false;
		}
	}
}
