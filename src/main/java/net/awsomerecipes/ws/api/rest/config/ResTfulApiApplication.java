package net.awsomerecipes.ws.api.rest.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages="net.awsomerecipes.ws.api.rest")
@EntityScan("net.awsomerecipes.ws.api.rest.beans")
@EnableJpaRepositories("net.awsomerecipes.ws.api.rest.daos")
public class ResTfulApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResTfulApiApplication.class, args);
	}

}
