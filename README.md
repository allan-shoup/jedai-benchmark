# jedai-benchmarks
[JMH](https://github.com/openjdk/jmh)-based microbenchmarks for the 
[JedAI](https://github.com/scify/JedAIToolkit) project.

## Building

```bash
mvn clean package
```

Will generate a benchmarks.jar file in the target folder.

## Example commands
### Run all benchmarks:

```bash
java -jar target/benchmarks.jar
```

### Run a specific benchmark
Specific benchmarks can be run using a regular expression pattern.

```bash
java -jar target/benchmarks.jar ".*EntityProfileBenchmark\.hashCode"
```

### Troubleshoot regular expression pattern
If you're attempting to specify a regular expression pattern and it's not picking up what you want,
enabling EXTRA verbose logging will let you see what it is attempting to match.

```bash
java -jar target/benchmarks.jar "EntityProfileBenchmark\.hashCode" -v EXTRA
```

### Output to JSON files

```bash
java -jar target/benchmarks.jar -rf json
```


### Help

```bash
java -jar target/benchmarks.jar -h
```
