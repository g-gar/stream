package com.ggar.stream;

import com.ggar.stream.model.StreamType;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

public interface Stream<T> {

	<R> Stream<R> map(Function<T,R> fn);
	Stream<T> filter(Predicate<T> filter);
	<A, R extends Collection<A>> R collect(Collector<A, ?, R> collector);

	default ParallelStream<T> parallel() {
		throw new RuntimeException("Unsupported");
	}
	default SequentialStream<T> sequential() {
		throw new RuntimeException("Unsupported");
	}

	StreamType getStreamType();
}
