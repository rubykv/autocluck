package com.autocluck.tweet_scheduler.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories(basePackages = "com.autocluck.tweet_scheduler.repository")
public class DatabaseConfig extends AbstractMongoClientConfiguration {

	@Override
	protected String getDatabaseName() {
		return "test";
	}

	@Override
	protected String getMappingBasePackage() {
		return "com.autocluck.tweet_scheduler.repository";
	}

	@Override
	public MongoClient mongoClient() {
		final ConnectionString connectionString = new ConnectionString("mongodb://34.201.66.129:27017/test");
		final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString).build();
		return MongoClients.create(mongoClientSettings);
	}

}
