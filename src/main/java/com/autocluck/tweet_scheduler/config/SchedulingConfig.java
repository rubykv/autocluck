package com.autocluck.tweet_scheduler.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan("com.autocluck.tweet_scheduler.service")
public class SchedulingConfig {

}
