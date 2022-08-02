package com.autocluck.tweet_scheduler.service;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleActionsService {
	Logger logger = LoggerFactory.getLogger(ScheduleActionsService.class);

	@Autowired
	StatusActionService statusActionService;
	
	@Autowired
	FollowersActionService followersActionService;

	@Scheduled(fixedRate = 3, timeUnit = TimeUnit.HOURS)
	public void tweet() {
		statusActionService.updateStatus();
	}
	
	@Scheduled(fixedRate = 24, timeUnit = TimeUnit.HOURS)
	public void checkForNewFollowers() {
		//followersActionService.thankFollowers();
	}
}
