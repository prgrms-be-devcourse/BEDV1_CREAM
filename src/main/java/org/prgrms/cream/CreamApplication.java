package org.prgrms.cream;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CreamApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
		+ "classpath:application.yml,"
		+ "classpath:application-local.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(CreamApplication.class)
			.properties(APPLICATION_LOCATIONS)
			.run(args);
	}

}
