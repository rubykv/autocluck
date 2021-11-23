package com.autocluck.tweet_scheduler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class TweetSchedulerConfig {
	
	private String key;
	private String secret;
	private String accesstoken;
	private String tokensecret;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public String getTokensecret() {
		return tokensecret;
	}
	public void setTokensecret(String tokensecret) {
		this.tokensecret = tokensecret;
	}
}
