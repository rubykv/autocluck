package com.autocluck.tweet_scheduler.model;

import org.springframework.web.multipart.MultipartFile;

public class CreateTweetRequest {
	private String content;
	private String name;
	private MultipartFile img;
	private boolean hasAttachment;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MultipartFile getImg() {
		return img;
	}

	public void setImg(MultipartFile img) {
		this.img = img;
	}

	public boolean isHasAttachment() {
		return hasAttachment;
	}

	public void setHasAttachment(boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

}
