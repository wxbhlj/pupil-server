package com.vivid.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.service.Parameter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration

@EnableSwagger2
public class Swagger2Config {

	@Bean
	public Docket createRestApi() {
		String auth =  "auth";
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
		aParameterBuilder.name("Authorization").defaultValue(auth).description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		List<Parameter> aParameters = new ArrayList<Parameter>();
		aParameters.add(aParameterBuilder.build());
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo()) 
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.vivid"))
				.paths(PathSelectors.any())
				.build().globalOperationParameters(aParameters);
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Auth API")
				.version("1.0")
				.build();
	}

}