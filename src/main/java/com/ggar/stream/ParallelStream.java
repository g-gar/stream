package com.ggar.stream;

import com.ggar.stream.model.StreamType;

public interface ParallelStream<T> extends Stream<T> {

	public final static StreamType streamType = StreamType.PARALLEL;

	@Override
	public default ParallelStream<T> parallel() {
		return this;
	}

	@Override
	public default StreamType getStreamType() {
		return streamType;
	}
}
