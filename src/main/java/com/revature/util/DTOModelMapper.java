package com.revature.util;

import static com.revature.config.RegisterDTORunner.modelMapper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;

import javax.persistence.EntityManager;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.annotations.DTO;

public class DTOModelMapper extends RequestResponseBodyMethodProcessor {
	private EntityManager entityManager;

	public DTOModelMapper(ObjectMapper objectMapper, EntityManager entityManager) {
		super(Collections.singletonList(new MappingJackson2HttpMessageConverter(objectMapper)));
		this.entityManager = entityManager;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(DTO.class);
	}

	@Override
	protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
		binder.validate();
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		Object dto = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
		
		Object id = DTOUtil.getEntityId(dto);
		
		if (id == null) {
			// Produce an entity from the values in the DTO
			return modelMapper.map(dto, parameter.getParameterType());
		} else {
			// Use the Persistence Context and find the entity with the same type as the
			// parameter
			// From the discovered Id
			Object persistedObject = entityManager.find(parameter.getParameterType(), id);

			// Copy contents from DTO to pre-existing entity
			modelMapper.map(dto, persistedObject);
			
			return persistedObject;
		}
	}

	@Override
	protected <T> Object readWithMessageConverters(HttpInputMessage inputMessage, MethodParameter parameter,
			Type targetType) throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {
		
		// Check all annotations on the parameter
		for (Annotation ann : parameter.getParameterAnnotations()) {

			// Try to obtain an instance of the DTO annotation
			// Will be null if unable
			DTO dtoType = AnnotationUtils.getAnnotation(ann, DTO.class);

			// If the DTO annotation was found
			if (dtoType != null) {

				// Use the parent method to produce an instance of the provided DTO type,
				// instead of the target type
				return super.readWithMessageConverters(inputMessage, parameter, dtoType.value());
			}
		}

		// Due to the supportsParameter method, the parameter should always have the DTO
		// annotation
		// But if something went wrong, throw an exception
		throw new RuntimeException("Provided parameter missing required annotation");
	}
}
