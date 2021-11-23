package com.autocluck.tweet_scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autocluck.tweet_scheduler.model.CreateTweetRequest;
import com.autocluck.tweet_scheduler.model.TweetResponse;
import com.autocluck.tweet_scheduler.service.TweetService;

@RestController
@RequestMapping("/api/tweet")
public class TweetResource {
	
	@Autowired
	private TweetService tweetService;
	
	//POST
	@PostMapping(path="/save", consumes = "application/json", produces = "application/json")
	public ResponseEntity<TweetResponse> saveTweet(@RequestBody CreateTweetRequest request){
		tweetService.saveTweet(request);
		return null;
	}
	
	//PUT
	
	//DELETE

}
