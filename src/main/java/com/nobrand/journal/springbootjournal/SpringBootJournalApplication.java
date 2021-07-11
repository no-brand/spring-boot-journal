package com.nobrand.journal.springbootjournal;

import com.nobrand.journal.springbootjournal.domain.Journal;
import com.nobrand.journal.springbootjournal.repository.JournalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import java.io.PrintStream;


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

		Logger log = LoggerFactory.getLogger(SpringBootJournalApplication.class);

		/*
		* SpringApplication provides builder pattern, as fluent API.
		* It provides high readability.
		* */
		new SpringApplicationBuilder()
				.banner(new Banner() {
					@Override
					public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
						out.print("\n\nCustom Banner\n\n");
					}
				})
				.sources(SpringBootJournalApplication.class)
				.logStartupInfo(true)
				.listeners(new ApplicationListener<ApplicationEvent>() {
					/*
					* ApplicationEvent status can be printed with logger.
					* (ex) ApplicationContextInitializedEvent
					*      ApplicationPreparedEvent
					*      ApplicationStartedEvent
					*      ApplicationReadyEvent
					* */
					@Override
					public void onApplicationEvent(ApplicationEvent applicationEvent) {
						log.info(" > " + applicationEvent.getClass().getCanonicalName());
					}
				})
				.run(args);
	}

}
