package com.ggar.stream.pipeline;

import com.ggar.stream.PipelineProcessor;
import com.ggar.stream.model.exception.PipelineFailedCheckException;
import com.ggar.stream.model.exception.PipelineFailedExecutionException;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class DefaultSequentialPipelineProcessor<R> implements PipelineProcessor<R> {
	@Override
	public <T> R run(T element, List<Object> transformationList) throws PipelineFailedExecutionException, PipelineFailedCheckException {
		Iterator<Object> transformations = transformationList.iterator();
		Function<Object,Object> base = e -> e;
		Function fn = null;
		Object result = element;
		Boolean stop = false;
		while (!stop && transformations.hasNext()) {
			if (fn == null) {
				fn = base;
			}
			Object next = transformations.next(); // no coherence for Java SDK's Function API
			if (next.getClass().getAnnotatedInterfaces().length > 0
				&& next.getClass().getAnnotatedInterfaces()[0].getType().equals(Function.class)) {
				fn = fn.andThen((Function) next);
			} else if (next.getClass().getAnnotatedInterfaces().length > 0
				&& next.getClass().getAnnotatedInterfaces()[0].getType().equals(Predicate.class)) {
				Object temp = result;
				try {
					temp = this.doRun((Predicate) next, this.doRun(fn, temp));
				} catch (PipelineFailedExecutionException exception) {
					throw exception;
				} catch (PipelineFailedCheckException exception) {
					stop = true;
					throw exception;
				} finally {
					result = temp;
				}
			}
		}
		return (R) result;
	}

	private <T,R> R doRun(Function<T,R> fn, T element) throws PipelineFailedExecutionException {
		return fn.apply(element);
	}

	private <T, R extends T> R doRun(Predicate<T> fn, R element) throws PipelineFailedCheckException {
		if (!fn.test(element)) {
			throw new PipelineFailedCheckException();
		}
		return element;
	}
}
