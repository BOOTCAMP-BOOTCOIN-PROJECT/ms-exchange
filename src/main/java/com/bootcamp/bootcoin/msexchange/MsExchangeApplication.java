package com.bootcamp.bootcoin.msexchange;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;

import java.util.logging.Logger;

@OpenAPIDefinition(info = @Info(
		title = "OpenAPI Documentation",
		version = "1.0.0",
		description = "Exchange Service v1.0.0",
		contact = @Contact(name = "Samuel Luis", email = "samuel@company.com", url = "https://www.linkedin.com/in/samuel14luis/"),
		license = @License(name = "MIT License", url = "https://choosealicense.com/licenses/mit/")
))
@EnableEurekaClient
@SpringBootApplication
public class MsExchangeApplication implements CommandLineRunner {

	private static final Logger logger = Logger.getLogger(MsExchangeApplication.class.toString());
	private static String apiGateway;

	@Autowired
	private Environment env;

	@Override
	public void run(String... args) throws Exception {

		apiGateway = env.getProperty("bootcamp.bank.gateway.uri");

		//logger.log(Level.INFO, env.getProperty("spring.application.name"));
		logger.info("Java version: " + env.getProperty("java.version"));
		logger.info("Application name: " + env.getProperty("spring.application.name"));
		logger.info("Properties file upload status: " + env.getProperty("my-own-app.properties.status"));
		logger.info("Swagger: http://localhost:" + env.getProperty("server.port") +"/" + env.getProperty("springdoc.swagger-ui.path"));
		logger.info("Api Gateway: " + apiGateway);

	}

	public static String getApiGateway() { return apiGateway; }


	public static void main(String[] args) {

		SpringApplication.run(MsExchangeApplication.class, args);

	}

}

