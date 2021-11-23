package com.autocluck.tweet_scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TweetSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweetSchedulerApplication.class, args);
	}

}
