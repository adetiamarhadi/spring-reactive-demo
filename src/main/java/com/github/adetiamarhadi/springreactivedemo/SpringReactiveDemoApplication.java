package com.github.adetiamarhadi.springreactivedemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
public class SpringReactiveDemoApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringReactiveDemoApplication.class);

	private static String url = "http://localhost:8081?delay=2";

	private static WebClient webClient = WebClient.create(url);

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Instant start = Instant.now();

		Flux.range(1, 3)
				.flatMap(i -> webClient.get().uri("/person/{id}", i).exchange()
						.flatMap(response -> response.toEntity(Person.class)))
				.blockLast();

		logTime(start);
	}

	private void logTime(Instant start) {
		log.info("elapsed time: {} ms", Duration.between(start, Instant.now()).toMillis());
	}

}
