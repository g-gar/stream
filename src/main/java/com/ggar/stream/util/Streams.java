package com.ggar.stream.util;

import com.ggar.stream.Stream;
import com.ggar.stream.stream.DefaultSequentialStream;
import lombok.SneakyThrows;

import java.util.Collection;

public class Streams {

	@SneakyThrows
	public static <T> Stream<T> stream(Collection<T> elements) {
		return Instantiator.instantiate(DefaultSequentialStream.class, elements);
	}

	@SneakyThrows
	public static <T> Stream<T> stream(Collection<T> elements, Object...args) {
		return Instantiator.instantiate(DefaultSequentialStream.class, elements, args);
	}

	@SneakyThrows
	public static <T> Stream<T> stream(Class<? extends Stream<T>> type, Collection<T> elements) {
		return Instantiator.instantiate(type, elements);
	}

	@SneakyThrows
	public static <T> Stream<T> stream(Class<? extends Stream<T>> type, Collection<T> elements, Object...args) {
		return Instantiator.instantiate(type, elements, args);
	}
}
