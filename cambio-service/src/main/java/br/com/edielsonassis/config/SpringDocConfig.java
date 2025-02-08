package br.com.edielsonassis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SpringDocConfig {

	@Value("${springdoc.server.url}")
	private String springDocServer;

	@Value("${springdoc.server.description}")
	private String springDocServerDescription;

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components())
				.addServersItem(new Server()
					.url(springDocServer)
					.description(springDocServerDescription))
				.info(new Info()
					.title("Cambio Service API")
					.version("v1.0.0")
					.description("Rest API of the Cambio application")
					.termsOfService("https://edielsonassis.com.br/termos-a-serem-definidos")
					.license(new License()
							.name("Apache 2.0")
							.url("https://github.com/edielson-assis/book-and-cambio-microservices/blob/main/LICENSE"))
					.contact(new Contact()
							.name("Edielson Assis")
							.email("grizos.ed@gmail.com")
							.url("https://github.com/edielson-assis")));
	}
}