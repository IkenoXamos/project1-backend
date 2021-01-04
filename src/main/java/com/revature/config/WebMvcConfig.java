package com.revature.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.util.DTOModelMapper;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final ApplicationContext applicationContext;
	private final EntityManager entityManager;

	@Autowired
	private RequestMappingHandlerAdapter adapter;

	// Prioritize Custom Argument Resolver over built in Resolvers
	@PostConstruct
	public void prioritizeCustomArgumentMethodHandlers() {
		// Get all Resolvers
		List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>(adapter.getArgumentResolvers());
		
		// Get Only Custom Resolvers
		List<HandlerMethodArgumentResolver> customResolvers = adapter.getCustomArgumentResolvers();
		
		// Remove Custom Resolvers and put them back in the front
		argumentResolvers.removeAll(customResolvers);
		argumentResolvers.addAll(0, customResolvers);
		
		// Replace old list with newly re-ordered list
		adapter.setArgumentResolvers(argumentResolvers);
	}

	@Autowired
	public WebMvcConfig(ApplicationContext applicationContext, EntityManager entityManager) {
		this.applicationContext = applicationContext;
		this.entityManager = entityManager;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);

		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().applicationContext(this.applicationContext)
				.build();
		resolvers.add(new DTOModelMapper(objectMapper, entityManager));
	}
}
