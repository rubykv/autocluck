package com.autocluck.tweet_scheduler.service;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autocluck.tweet_scheduler.model.CreateTweetRequest;
import com.autocluck.tweet_scheduler.model.Tweet;
import com.autocluck.tweet_scheduler.repository.TweetRepository;

@Service
public class TextConverter extends JPanel {
	private static final long serialVersionUID = 5811668891739886609L;
	
	Logger logger = LoggerFactory.getLogger(TextConverter.class);

	@Autowired
	private TweetRepository tweetRepository;
	
	
	public void convertTextToImage(CreateTweetRequest req) {
		try {
			BufferedImage bi = createImageWithText(req.getContent());
			File outputfile = new File("src/main/resources/images/"+req.getName()+".png");
			ImageIO.write(bi, "png", outputfile);
			
			Tweet tweet = new Tweet();
			tweet.setName(req.getName());
			tweet.setContent(null);
			tweet.setDate(LocalDateTime.now());
			tweet.setHasAttachment(true);
			tweet.setFileExtension("png");
			tweetRepository.save(tweet);
		} catch (Exception e) {
			logger.error("Couldn't update tweet ", e);
		}
	}

	private static BufferedImage createImageWithText(String content) {

		BufferedImage bufferedImage = new BufferedImage(1200, 675, BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.getGraphics();
		g.setFont(new Font("TimesRoman", Font.BOLD, 50));
		
        String[] chunks = content.split("\\|");

        //TODO change to dynamic allocation
		g.drawString(chunks[0], 20, 50);
		g.drawString(chunks[1], 20, 250);
		g.drawString(chunks[2], 20, 450);
		g.drawString(chunks[3], 20, 650);

		return bufferedImage;

	}
}
