package com.ggar.stream.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;

public class Instantiator {

	public static <I, C> I instantiate(Class<C> type, Object...args) throws InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Class<?>[] parameterTypes = Arrays.stream(args)
			.map(Object::getClass)
			.collect(Collectors.toList())
			.toArray(new Class[args.length]);
		Constructor<?>[] constructors = type.getConstructors();
		Iterator<Constructor<?>> iterator = Arrays.stream(constructors).iterator();
		I instance = null;
		while (instance == null && iterator.hasNext()) {
			try {
				instance = (I) iterator.next().newInstance(args);
			} catch (Exception e) {

			}
		}

		return Objects.requireNonNull(instance, String.format("No valid constructor found for class %s", type));
	}
}
