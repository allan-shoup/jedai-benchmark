package org.scify.jedai;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class BaselineBenchmark {
  @Benchmark
  @Fork(value = 3, warmups = 1)
  @Measurement(iterations = 5)
  public void baseline() {
    // Do nothing, this is a baseline to gauge how fast the machine is
  }
}
