package org.scify.jedai.datamodel;

import com.google.common.collect.ImmutableList;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 5, warmups = 1)
@Measurement(batchSize = 1, iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
// @Warmup(iterations = 5)
public class EntityProfileBenchmark {
  @State(Scope.Thread)
  public static class EPState {
    /** The EntityProfile corresponding to {@code itr1}. */
    EntityProfile ep1;

    /** The EntityProfile corresponding to {@code itr2}. */
    EntityProfile ep2;
    /**
     * When comparing two entity profile objects, the benchmark code will take the entity profile at
     * itr1 and compare it to the entity profile at itr2. itr1 will stay fixed until itr2 has
     * iterated through the list one time and then itr1 will get incremented. Both values will reset
     * to the first element when they reach the end of the profiles list.
     */
    Iterator<EntityProfile> itr1;

    Iterator<EntityProfile> itr2;

    ImmutableList<EntityProfile> profiles;

    public static Object loadSerializedObject(String fileName) {
      Object object;
      try (InputStream is = ClassLoader.getSystemResourceAsStream(fileName);
          InputStream buffer = new BufferedInputStream(is);
          ObjectInput input = new ObjectInputStream(buffer); ) {
        object = input.readObject();
      } catch (RuntimeException e) {
        throw e;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

      return object;
    }

    /** Called before benchmark method invocation. Advances itr2 by one element. */
    @Setup(Level.Invocation)
    public void setUpInvocation() {
      if (!itr1.hasNext()) {
        itr1 = profiles.iterator();
        ep1 = itr1.next();
      }
      if (!itr2.hasNext()) {
        ep1 = itr1.next();
        itr2 = profiles.iterator();
      }
      ep2 = itr2.next();
    }

    /** Called before a set of invocations. */
    @Setup(Level.Iteration)
    public void setUpIteration() {
      itr1 = profiles.iterator();
      ep1 = itr1.next();
      itr2 = profiles.iterator();
    }

    @SuppressWarnings("unchecked")
    @Setup(Level.Trial)
    public void setUpTrial() {
      String path = "data/cleanCleanErDatasets/amazonProfiles";
      profiles = ImmutableList.copyOf((List<EntityProfile>) loadSerializedObject(path));
    }
  }

  @Benchmark
  public boolean equals(EPState state) {
    return state.ep1.equals(state.ep2);
  }

  @Benchmark
  public int hashCode(EPState state) {
    return state.ep2.hashCode();
  }
}
