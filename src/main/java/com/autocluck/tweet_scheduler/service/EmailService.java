package com.autocluck.tweet_scheduler.service;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autocluck.tweet_scheduler.model.EmailCred;
import com.autocluck.tweet_scheduler.repository.EmailCredRepository;

@Service
public class EmailService {
	Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private EmailCredRepository emailCredRepository;

	private static Properties prop;
	private String uname;
	private String pwd;
	private String toMail;
	private String fromMail;

	@PostConstruct
	public void fetchEmailCredentials() {
		EmailCred emailCred = emailCredRepository.findAll().get(0);
		uname = emailCred.getUname();
		pwd = emailCred.getPwd();
		toMail = emailCred.getToMail();
		fromMail = emailCred.getFromMail();
	}

	static {
		prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

	}

	public void sendMail() throws Exception {

		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(uname, pwd);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromMail));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));
		message.setSubject("AutoCluck Alert :: No More Tweets to Cluck");

		String msg = "This is to alert the autocluck user that the stored data has all been sent,<b> Please Take Action Immediately </b>";
		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		message.setContent(multipart);
		Transport.send(message);
		logger.info("Email Notification Sent");
	}
}
