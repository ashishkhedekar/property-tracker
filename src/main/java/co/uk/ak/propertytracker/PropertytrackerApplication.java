package co.uk.ak.propertytracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PropertytrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertytrackerApplication.class, args);
	}

}
