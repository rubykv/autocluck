package com.autocluck.tweet_scheduler.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckResource {
	
	@GetMapping(path="/")
	public HttpStatus healthCheck(){
		return HttpStatus.OK;
	}
	
	@GetMapping(path="/api/tweet/health")
	public String healthCheckWithMessage() {
		return "All Good";
	}

}
