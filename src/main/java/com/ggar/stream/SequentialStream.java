package com.ggar.stream;

import com.ggar.stream.model.StreamType;

public interface SequentialStream<T> extends Stream<T> {

	public final static StreamType streamType = StreamType.SEQUENTIAL;

	@Override
	public default SequentialStream<T> sequential() {
		return this;
	}

	@Override
	public default StreamType getStreamType() {
		return streamType;
	}

}
