package com.ggar.stream;

import com.ggar.stream.model.exception.PipelineFailedCheckException;
import com.ggar.stream.model.exception.PipelineFailedExecutionException;

import java.util.List;

public interface PipelineProcessor<R> {

	public <T> R run(T element, List<Object> transformationList) throws PipelineFailedExecutionException, PipelineFailedCheckException;

}
