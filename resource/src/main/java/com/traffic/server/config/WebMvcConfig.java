package com.traffic.server.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traffic.server.utils.OSSClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class WebMvcConfig extends WebMvcConfigurationSupport {
	@Value("${swagger.show}")
	private boolean swaggerShow;
	@Value("${oss.endpoint}")
	private String endpoint;
	@Value("${oss.accessKeyId}")
	private String accessKeyId;
	@Value("${oss.accessKeySecret}")
	private String accessKeySecret;
	@Value("${oss.bucketName}")
	private String bucketName;
	@Value("${oss.filedir}")
	private String filedir;

	@Bean
	public OSSClientUtil ossClientUtil() {
		return new OSSClientUtil(endpoint,accessKeyId,accessKeySecret,bucketName,filedir);
	}

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

    /**
     * 跨域支持
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600 * 24);
    }

	@Bean
	public Docket createRestApi() {
		//添加head参数start
		ParameterBuilder tokenPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").defaultValue("Bearer ").required(true).build();
		pars.add(tokenPar.build());
		//添加head参数end

		return new Docket(DocumentationType.SWAGGER_2)
				.enable(swaggerShow)
				.apiInfo(apiInfo())
				.select()
				//为当前包路径
				.apis(RequestHandlerSelectors.basePackage("com.traffic.server"))
//				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build()
				// 添加head参数
				.globalOperationParameters(pars);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("API 文档")
				.description("静态交通 api文档")
				.version("1.0")
				.build();
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/");
	}

}
