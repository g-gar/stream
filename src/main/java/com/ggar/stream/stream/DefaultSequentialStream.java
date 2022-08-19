package com.ggar.stream.stream;

import com.ggar.stream.PipelineProcessor;
import com.ggar.stream.SequentialStream;
import com.ggar.stream.Stream;
import com.ggar.stream.model.exception.PipelineFailedExecutionException;
import com.ggar.stream.pipeline.DefaultSequentialPipelineProcessor;
import com.ggar.stream.util.Instantiator;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DefaultSequentialStream<T> implements SequentialStream<T> {

	@NonNull
	@ToString.Exclude
	Collection<T> elements;

	@NonNull
	@ToString.Exclude
	List<Object> transformations;

	public DefaultSequentialStream() {
		this.elements = new ArrayList<>();
		this.transformations = new LinkedList<>();
	}

	public DefaultSequentialStream(List<T> elements) {
		this.elements = elements;
		this.transformations = new LinkedList<>();
	}

	@Override
	public <R> Stream<R> map(Function<T,R> fn) {
		try {
			transformations.add(fn);
			return Instantiator.instantiate(this.getClass(), elements, transformations);
		} catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Stream<T> filter(Predicate<T> filter) {
		try {
			transformations.add(filter);
			return Instantiator.instantiate(this.getClass(), elements, transformations);
		} catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <A, R extends Collection<A>> R collect(Collector<A, ?, R> collector) {
//		TODO: wth is java sdk's stream api doing here?
		List<A> processedElements = new ArrayList<>();
		PipelineProcessor<A> processor = new DefaultSequentialPipelineProcessor<A>();
		for (T element : elements) {
			try {
				A temp = processor.run(element, transformations);
				processedElements.add(temp);
			} catch (PipelineFailedExecutionException exception) {
//				TODO: log event
				exception.printStackTrace();
			} catch (Exception exception) {
//				ignore
			}
		}
		return processedElements.stream()
			.collect(collector);
	}

}
