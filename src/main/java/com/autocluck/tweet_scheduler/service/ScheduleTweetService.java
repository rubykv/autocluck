package com.autocluck.tweet_scheduler.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.autocluck.tweet_scheduler.external_client.TwitterClient;
import com.autocluck.tweet_scheduler.model.Identity;
import com.autocluck.tweet_scheduler.model.Tweet;
import com.autocluck.tweet_scheduler.repository.IdentityRepository;
import com.autocluck.tweet_scheduler.repository.TweetRepository;

import twitter4j.StatusUpdate;
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

	@Autowired
	private EmailService emailService;

	@Autowired
	ResourceLoader resourceLoader;

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
			String name = selectedTweet.getName();
			String fileExt = selectedTweet.getFileExtension();
			List<Identity> identities = identityRepository.findAll();

			File file = null;
			String path = null;
			if (selectedTweet.isHasAttachment()) {
				path = "src/main/resources/images/" + name + "." + fileExt;
				file = getFile(path);
			}
			postTweet(tweetToPublish, identities, file);
			deleteTweetAndMediaAfterPosting(name, path);
		} else {
			logger.info("No More Tweets To Post");
			try {
				emailService.sendMail();
			} catch (Exception ex) {
				logger.error("Notification not sent");
			}
		}
	}

	private void postTweet(String tweetToPublish, List<Identity> identities, File file)
			throws InterruptedException, ExecutionException, IOException, TwitterException {
		if (null != file) {
			StatusUpdate status = new StatusUpdate(tweetToPublish);
			status.setMedia(file);
			twitterClient.doPostWithMedia(status, identities.get(0));
		} else {
			twitterClient.doPost(tweetToPublish, identities.get(0));
		}
	}

	private void deleteTweetAndMediaAfterPosting(String name, String path) throws IOException {
		tweetRepository.deleteTweetByName(name);
		if (null != path) {
			Files.deleteIfExists(Paths.get(path));
		}
	}

	private File getFile(String path) throws FileNotFoundException {
		try {
			Path filepath = Paths.get(path);
			if (Files.exists(filepath)) {
				return ResourceUtils.getFile(path);
			}
			return null;
		} catch (Exception ex) {
			return null;
		}
	}
}
