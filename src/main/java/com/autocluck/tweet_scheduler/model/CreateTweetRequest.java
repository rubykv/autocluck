package com.autocluck.tweet_scheduler.model;

import java.util.List;

public class CreateTweetRequest {
	private String content;
	private String link;
	private List<String> hashTag;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<String> getHashTag() {
		return hashTag;
	}
//set max 3
	public void setHashTag(List<String> hashTag) {
		this.hashTag = hashTag;
	}
}
