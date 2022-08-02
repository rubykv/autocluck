package com.autocluck.tweet_scheduler.external_client;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autocluck.tweet_scheduler.model.Identity;
import com.autocluck.tweet_scheduler.repository.IdentityRepository;

import twitter4j.DirectMessage;
import twitter4j.PagableResponseList;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

@Component
public class TwitterClient {
    Logger logger = LoggerFactory.getLogger(TwitterClient.class);
    
    @Autowired
	private IdentityRepository identityRepository;
    
    private Identity identity = null;

	@PostConstruct
	public void fetchKey() {
		List<Identity> identities = identityRepository.findAll();
		identity = identities.get(0);
	}
	
	public void doPost(String updateStatus) throws InterruptedException, ExecutionException, IOException, TwitterException {
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
	
	public void doPostWithMedia(StatusUpdate updateStatus) throws InterruptedException, ExecutionException, IOException, TwitterException {
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
	
	public PagableResponseList<User> getFollowersList(String id, long cursor) throws InterruptedException, ExecutionException, IOException, TwitterException {
		try {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(identity.getKey(), identity.getSecret());
			AccessToken accessToken = new AccessToken(identity.getAccesstoken(), identity.getTokensecret());
			twitter.setOAuthAccessToken(accessToken);
			PagableResponseList<User> usrs = twitter.getFollowersList(id, cursor);
			return usrs;
		} catch (TwitterException te) {
			logger.error("Error occurred while updating tweet " + te);
			throw te;
		}
	}
	
	public DirectMessage thankFollower(long id, String message) throws InterruptedException, ExecutionException, IOException, TwitterException {
		try {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(identity.getKey(), identity.getSecret());
			AccessToken accessToken = new AccessToken(identity.getAccesstoken(), identity.getTokensecret());
			twitter.setOAuthAccessToken(accessToken);
			DirectMessage drMsg = twitter.sendDirectMessage(id, message);
			return drMsg;
		} catch (TwitterException te) {
			logger.error("Error occurred while updating tweet " + te);
			throw te;
		}
	}
}
