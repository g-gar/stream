# Stream
Java SDK v1.8 Stream API revisited

## Example
Same old stream API's notation except from it's internals:
```java
public List<Integer> test() {
    List<Integer> elements = IntStream.range(0,10).boxed().collect(Collectors.toList());
	return Streams.stream(elements)
		.map(e -> e*e)
		.map(e -> e+1)
		.filter(e -> (e >= 20) && e <= 40)
		.collect(Collectors.toCollection(ArrayList::new));
}
```
This project aims to simplify java's stream API with a more civilized API with implementations just for the type of the stream (sequential vs parallel) instead of an implementation for each primitive type.
As of being a prototype for now, there are a few parts were the older API is still being used (while `java.util.stream.Collector` usages stops being so menacing) and lacks an implementation for parallel streams (until inner works of `java.util.Spliterator` doesn't become a bit more understandable). It also needs some improvements for the transformations pipeline and a few more functionalities from old's API (`take`, `reduce`, `sorted`, `limit`...).

Some planned improvements are the inclusion of a richer transformation set (by now it's just about `java.util.function.Function`s and `java.util.function.Predicate`s) which could enable creating more complex pipelines with features like:
* mapIf/mapIfNot
* rollbacks in case of an exception
* pipeline state monitoring
* trazability
* contexts