package com.revature.advice;

import static com.revature.config.RegisterDTORunner.classMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.revature.util.DTOUtil;

@ControllerAdvice
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DTOAspect implements ResponseBodyAdvice {
	// Methods for ResponseBodyAdvice
	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {

		// Use a more convenient format for the return type
		ResolvableType relevantType = ResolvableType.forMethodParameter(returnType);

		relevantType = findType(relevantType);

		// Apply this ResponseBodyAdvice only if the relevant type is a registered DTO
		return classMapper.get(relevantType.resolve()) != null;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

		ResolvableType root = ResolvableType.forMethodParameter(returnType);

		// If the body was nested, then map each contained element
		// Otherwise just map the one object
		return (isNested(root)) ? mapCollection(body) : DTOUtil.map(body);
	}
	// End of Methods for ResponseBodyAdvice

	private List<Object> mapCollection(Object body) {

		// If the body was a Collection, then map each element within it
		return ((Collection<Object>) body).stream().map((Object o) -> (DTOUtil.map(o))).collect(Collectors.toList());
	}

	private boolean isNested(ResolvableType type) {
		// If a ResponseEntity was used, find the nested type
		if (type.resolve().equals(ResponseEntity.class)) {
			type = type.getGeneric(0);
		}

		return type.isArray() || java.util.Collection.class.isAssignableFrom(type.resolve());
	}

	private ResolvableType findType(ResolvableType root) {
		// If a ResponseEntity was used, find the nested type
		if (root.resolve().equals(ResponseEntity.class)) {
			root = root.getGeneric(0);
		}

		// If the type was an array or Collection, step only 1 level deeper
		if (isNested(root)) {
			if (root.isArray()) {
				root = root.getComponentType();
			} else {
				root = root.asCollection().getGeneric(0);
			}
		}

		return root;
	}
}
