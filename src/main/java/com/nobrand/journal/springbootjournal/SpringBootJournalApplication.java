package com.nobrand.journal.springbootjournal;

import com.nobrand.journal.springbootjournal.domain.Journal;
import com.nobrand.journal.springbootjournal.repository.JournalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;


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
public class SpringBootJournalApplication implements CommandLineRunner, ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBootJournalApplication.class);

	/*
	* InitializingBean helps for initializing something.
	* When Spring engine make instance to initialize, this is always called.
	* */
	@Bean
	InitializingBean saveData(JournalRepository repo) {
		return() -> {
			log.info("InitializingBean: Initialize data");
			repo.save(new Journal("Start Spring Boot", "I decided to study Spring Boot",
					"07/08/2021"));
			repo.save(new Journal("Make Spring Boot Project", "I made the first Spring Boot Project",
					"07/09/2021"));
			repo.save(new Journal("Analyze Spring Boot", "Learn details of Spring Boot",
					"07/10/2021"));
		};
	}

	public static void main(String[] args) {
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

	/*
	* Both ApplicationRunner, CommandLineRunner implements run methods
	* to prepare initializing Spring boot application.
	* TODO: difference against @Bean InitializingBean to initialize data above.
	*
	* Between ApplicationStartedEvent and ApplicationReadyEvent, below run methods are called.
	* @Bean InitializingBean created before ApplicationStartedEvent for IoC,
	* so InitializingBean called the most front among them.
	* */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("ApplicationRunner.run: " + args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("CommandLineRunner.run: " + Arrays.toString(args));
	}
}

/*
* Bean can receive Spring boot arguments.
*
* $ java -jar target/spring-boot-journal-0.0.1-SNAPSHOT.jar --enable arg1 arg2
* :  > has enable option
* :  > other options
* :    arg1
* :    arg2
* */
@Component
class MyComponent {

	private static final Logger log = LoggerFactory.getLogger(MyComponent.class);

	@Autowired
	public MyComponent(ApplicationArguments args) {
		boolean enable = args.containsOption("enable");
		if (enable)
			log.info(" > has enable option");

		List<String> _args = args.getNonOptionArgs();
		log.info(" > other options");
		if (!_args.isEmpty())
			_args.forEach(file -> log.info("   " + file));
	}

}