package com.revature.util;

import static com.revature.config.RegisterDTORunner.classMapper;
import static com.revature.config.RegisterDTORunner.modelMapper;

import java.lang.reflect.Field;
import java.util.Collection;

import javax.persistence.Id;

@SuppressWarnings("unchecked")
public class DTOUtil {

	public static Object getEntityId(Object dto) {

		// Check all fields of the object
		for (Field field : dto.getClass().getDeclaredFields()) {

			// Find the field annotated with @Id
			if (field.getAnnotation(Id.class) != null) {
				try {

					// Return the Id
					field.setAccessible(true);
					Object id = field.get(dto);
					field.setAccessible(false);
					return id;
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return null;
	}

	public static Object map(Object src) {
		
		if(src.getClass().isArray() || Collection.class.isAssignableFrom(src.getClass())) {
			throw new RuntimeException("Cannot map collections");
		}

		if (classMapper.containsKey(src.getClass())) {
			return modelMapper.map(src, classMapper.get(src.getClass()));
		}

		return null;
	}
}
