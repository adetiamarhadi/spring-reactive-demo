package com.github.adetiamarhadi.springreactivedemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
public class SpringReactiveDemoApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringReactiveDemoApplication.class);

	private static String url = "http://localhost:8081?delay=2";

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveDemoApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(url));
		return restTemplate;
	}

	@Autowired
	RestTemplate restTemplate;

	@Override
	public void run(String... args) throws Exception {

		Instant start = Instant.now();

		for (int i = 0; i < 3; i++) {
			restTemplate.getForObject("/person/{id}", Person.class, i);
		}

		logTime(start);
	}

	private void logTime(Instant start) {
		log.info("elapsed time: {} ms", Duration.between(start, Instant.now()).toMillis());
	}

}
