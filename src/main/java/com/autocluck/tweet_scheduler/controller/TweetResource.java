package com.autocluck.tweet_scheduler.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autocluck.tweet_scheduler.model.CreateTweetRequest;
import com.autocluck.tweet_scheduler.model.Tweet;
import com.autocluck.tweet_scheduler.model.TweetResponse;
import com.autocluck.tweet_scheduler.service.TextConverter;
import com.autocluck.tweet_scheduler.service.TweetService;

@RestController
@RequestMapping("/api/tweet")
public class TweetResource {
	
	@Autowired
	private TweetService tweetService;
	
	@Autowired
	private TextConverter textConverter;
	
	//GET
	@GetMapping(path="/fetch/tweets")
	public ResponseEntity<List<Tweet>> fetchAllTweet(@RequestHeader(name = "Authorization", required= true)String authKey) {
		if(!tweetService.isAuthorized(authKey)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(tweetService.getAllTweets(), HttpStatus.OK);
	}
	
	//POST
	@PostMapping(path="/save", consumes = "application/json", produces = "application/json")
	public ResponseEntity<TweetResponse> saveTweet(@RequestHeader(name = "Authorization", required= true)String authKey, @RequestBody CreateTweetRequest request){
		if(!tweetService.isAuthorized(authKey)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		if(request.getContent().length() > 280) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		tweetService.saveTweet(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	//DELETE
	@DeleteMapping(path="/delete")
	public ResponseEntity<TweetResponse> deleteTweet(@RequestHeader(name = "Authorization", required= true)String authKey, @RequestParam(value = "tweet") String tweet) {
		if(!tweetService.isAuthorized(authKey)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		tweetService.deleteTweet(tweet);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	// POST
	@PostMapping(path = "/save/media", consumes = {"multipart/form-data" }, produces = "application/json")
	public ResponseEntity<Void> saveTweetWithMedia(@ModelAttribute CreateTweetRequest req) throws IllegalStateException, IOException {
		tweetService.saveTweetWithMedia(req);
		return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	// POST
	@PostMapping(path = "/save/text", consumes = "application/json", produces = "application/json")
	public ResponseEntity<TweetResponse> saveTweetToConvert(@RequestHeader(name = "Authorization", required = true) String authKey, @RequestBody CreateTweetRequest request) {
		if (!tweetService.isAuthorized(authKey)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		textConverter.convertTextToImage(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
