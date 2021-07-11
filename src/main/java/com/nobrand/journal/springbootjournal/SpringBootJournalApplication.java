package com.nobrand.journal.springbootjournal;

import com.nobrand.journal.springbootjournal.domain.Journal;
import com.nobrand.journal.springbootjournal.repository.JournalRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/*
* @SpringBootApplication is combination of
*   @SpringBootConfiguration, @EnableAutoConfiguration, @ComponentScan.
* Spring Boot does not generate code; however we do not have to care about web.xml, @Configuration, Bean configuration.
*
* Specifically, @EnableAutoConfiguration helps to distinguish components.
* Also, Spring boot works standalone with tomcat as non-functional element.
* You can replace tomcat with others, such as jetty or undertow.
* */
@SpringBootApplication
public class SpringBootJournalApplication {

	/*
	* InitializingBean helps for initializing something.
	* When Spring engine make instance to initialize, this is always called.
	* */
	@Bean
	InitializingBean saveData(JournalRepository repo) {
		return() -> {
			repo.save(new Journal("Start Spring Boot", "I decided to study Spring Boot",
					"07/08/2021"));
			repo.save(new Journal("Make Spring Boot Project", "I made the first Spring Boot Project",
					"07/09/2021"));
			repo.save(new Journal("Analyze Spring Boot", "Learn details of Spring Boot",
					"07/10/2021"));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJournalApplication.class, args);
	}

}
