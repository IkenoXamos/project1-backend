package com.revature.config;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import com.revature.annotations.RegisterDTO;

@Component
@SuppressWarnings({"rawtypes", "unchecked"})
public class RegisterDTORunner implements CommandLineRunner {

	public static final Map<Class, Class> classMapper = new HashMap<>();
	public static final ModelMapper modelMapper = new ModelMapper();

	@Override
	public void run(String... args) throws Exception {
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		System.out.println("Registering DTOs...");

		// Scan through the Classpath, looking for classes annotated with @RegisterDTO
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(RegisterDTO.class));

		// Search through starting with the com.revature package
		for (BeanDefinition bd : scanner.findCandidateComponents("com.revature")) {
			
			// Obtain the Class Object
			Class dtoClass = Class.forName(bd.getBeanClassName());
			
			// Grab the RegisterDTO annotation object from it
			RegisterDTO registerDTO =  AnnotationUtils.getAnnotation(dtoClass.getAnnotation(RegisterDTO.class), RegisterDTO.class);
			
			// And grab the declared entity class
			Class entityClass = registerDTO.value();

			// Associate the classes together
			classMapper.put(entityClass, dtoClass);
			
			modelMapper.typeMap(entityClass, dtoClass);
			modelMapper.validate();
		}
	}
}
