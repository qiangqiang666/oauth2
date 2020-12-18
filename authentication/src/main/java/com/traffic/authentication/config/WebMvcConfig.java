package com.traffic.authentication.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class WebMvcConfig extends WebMvcConfigurationSupport {
	@Value("${swagger.show}")
	private boolean swaggerShow;


	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(jackson2HttpMessageConverter());
	}

	@Bean
	public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		converter.setObjectMapper(mapper);
		return converter;
	}

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.enable(swaggerShow)
				.apiInfo(apiInfo())
				.select()
				//为当前包路径
				.apis(RequestHandlerSelectors.basePackage("com.traffic.authentication"))
//				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("API 文档")
				.description("认证服务 api文档")
				.version("1.0")
				.build();
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/","classpath:/META-INF/resources/webjars/");
	}

}
