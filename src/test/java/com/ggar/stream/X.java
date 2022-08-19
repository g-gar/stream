package com.ggar.stream;

import com.ggar.stream.util.Streams;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class X {

	public static void main(String[] args) throws InvocationTargetException, InstantiationException, NoSuchMethodException, IllegalAccessException {
		x();
	}

	public static void x() throws InvocationTargetException, InstantiationException, NoSuchMethodException, IllegalAccessException {
		int debug = 0;

		Collector<Integer, ?, List<Integer>> collector = Collectors.toCollection(ArrayList::new);

		List<Integer> stream = Streams.stream(IntStream.range(0,10).boxed().collect(Collectors.toList()))
			.sequential()
			.map(e -> e*e)
//			.map(new Function<Integer,Integer>() { // seems like java.util.Function != lambda
//				@Override
//				public Integer apply(Integer integer) {
//					return integer * integer;
//				}
//			})
			.map(e -> e+1)
			.filter(e -> (e >= 20) && e <= 40)
			.collect(collector)
			;
		debug++;

	}

}
