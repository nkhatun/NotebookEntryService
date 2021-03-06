package com.demo.codingtask.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguaration {
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.demo.codingtask.api.v1.controller"))
                .paths(PathSelectors.any())
                .build();
    }

	private ApiInfo getApiInfo() {
		Contact contact = new Contact("Najma Khatun", "https://github.com/nkhatun", "nknajada@gmail.com");
        return new ApiInfoBuilder()
                .title("Demo Assignment Task")
                .description("Notebook Entry New Features")
                .version("1.0.0")
                .contact(contact)
                .build();
	}

}
