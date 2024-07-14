package com.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entity.Tutorial;
import com.example.repository.Tutorialrepository;

@SpringBootApplication
public class TutorialdevelopmentApplication implements CommandLineRunner {

	@Autowired
	private Tutorialrepository tutorialrepository;

	public static void main(String[] args) {
		SpringApplication.run(TutorialdevelopmentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Tutorial> all = List.of(Tutorial.builder().title("java").description("core java").published(false).build(),
				Tutorial.builder().title("c").description("core compiler").published(false).build(),
				Tutorial.builder().title("jee").description("advanced java").published(false).build(),
				Tutorial.builder().title("ai").description("gemini and openai").published(true).build(),
				Tutorial.builder().title("spring boot").description("rest api").published(true).build()

		);
		tutorialrepository.saveAll(all);
		System.out.println("---------------all saved------------------");

	}

}
