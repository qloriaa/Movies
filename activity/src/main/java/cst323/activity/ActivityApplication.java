package cst323.activity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "cst323.activity" })
public class ActivityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivityApplication.class, args);
	}

}
