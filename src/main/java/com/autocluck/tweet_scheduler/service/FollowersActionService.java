package com.autocluck.tweet_scheduler.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autocluck.tweet_scheduler.external_client.TwitterClient;
import com.autocluck.tweet_scheduler.model.Follower;
import com.autocluck.tweet_scheduler.repository.FollowerRepository;

import twitter4j.PagableResponseList;
import twitter4j.User;

@Service
public class FollowersActionService {
	Logger logger = LoggerFactory.getLogger(FollowersActionService.class);


	@Autowired
	private TwitterClient twitterClient;

	@Autowired
	FollowerRepository followerRepository;

	Follower follower = null;

	@PostConstruct
	public void fetchKey() {
		follower = followerRepository.findAll().get(0);
	}

	public void thankFollowers() {
		try {
			PagableResponseList<User> usrs = twitterClient.getFollowersList("rubyshiv", -1);
			long latestFollower = usrs.get(0).getId();
			String latestFollowerName = usrs.get(0).getScreenName();
			logger.info("Latest Follower:: {} , Last follower:: {}", latestFollower, follower.getUserId());

			for (User usr : usrs) {
				if (usr.getId() == follower.getUserId() || usr.getScreenName().equals(follower.getUserName())) {
					break;
				} else {
					String screenName = usr.getScreenName();
					long id = usr.getId();
					twitterClient.thankFollower(id, String.format("Thanks for following, %s. Hope you enjoy my tweets!", screenName));
					logger.info("Follower {} thanked", screenName);
				}
			}
			
			if(latestFollower != follower.getUserId()) {
				followerRepository.deleteAll();
				Follower newFollow = new Follower();
				newFollow.setUserId(latestFollower);
				newFollow.setUserName(latestFollowerName);
				followerRepository.save(newFollow);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
