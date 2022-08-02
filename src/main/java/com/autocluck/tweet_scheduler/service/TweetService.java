package com.autocluck.tweet_scheduler.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autocluck.tweet_scheduler.AutoCluckUtil;
import com.autocluck.tweet_scheduler.model.CreateTweetRequest;
import com.autocluck.tweet_scheduler.model.Cred;
import com.autocluck.tweet_scheduler.model.Tweet;
import com.autocluck.tweet_scheduler.repository.CredRepository;
import com.autocluck.tweet_scheduler.repository.TweetRepository;

@Service
public class TweetService {
	Logger logger = LoggerFactory.getLogger(TweetService.class);

	public static String key = null;
	public static String authString = null;

	@Autowired
	private TweetRepository tweetRepository;

	@Autowired
	private CredRepository credRepository;

	@PostConstruct
	public void fetchKey() {
		Cred cred = credRepository.findAll().get(0);
		key = cred.getKey();
		authString = cred.getAuthString();
	}

	public void saveTweet(CreateTweetRequest request) {
		try {
			Tweet tweet = new Tweet();
			tweet.setName(request.getName());
			tweet.setContent(request.getContent());
			tweet.setDate(LocalDateTime.now());
			tweetRepository.save(tweet);
		} catch (Exception ex) {
			logger.error("Couldn't save tweet ", ex);
		}
	}

	public void deleteTweet(String name) {
		tweetRepository.deleteTweetByName(name);
	}
	
	public boolean isAuthorized(String authKey) {
		try {
			if(!Objects.equals(AutoCluckUtil.decrypt(authKey, key), authString)) {
				return false;
			}
		} catch (Exception ex) {
			logger.error("Decryption operation failed", ex);
			return false;
		}
		return true;
	}
	
	public List<Tweet> getAllTweets() {
		return tweetRepository.findAll();
	}
	
	public void saveTweetWithMedia(CreateTweetRequest request) {
		try {
			String orgName = request.getImg().getOriginalFilename();
			String fileExt = orgName.split("\\.")[1];
			Path filepath = Paths.get("src/main/resources/images/", request.getName()+"."+fileExt);
			request.getImg().transferTo(filepath);
			
			Tweet tweet = new Tweet();
			tweet.setName(request.getName());
			tweet.setContent(request.getContent());
			tweet.setDate(LocalDateTime.now());
			tweet.setHasAttachment(request.isHasAttachment());
			tweet.setFileExtension(fileExt);
			tweetRepository.save(tweet);
		} catch (Exception ex) {
			logger.error("Couldn't save tweet ", ex);
		}
	}
}
