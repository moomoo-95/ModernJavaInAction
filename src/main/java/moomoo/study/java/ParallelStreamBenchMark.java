package moomoo.study.java;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs={"-Xms4G", "-Xmx4G"})
public class ParallelStreamBenchMark{
    private static final long N = 10_000_000L;

    @Benchmark
    public long sequentialSum() {
        return Stream.iterate(1L, i -> i + 1).limit(N)
                .reduce(0L, Long::sum);
    }

    @Benchmark
    public long parallelSum() {
        return Stream.iterate(1L, i -> i + 1).limit(N).parallel()
                .reduce(0L, Long::sum);
    }

    @Benchmark
    public long iterativeSum() {
        int result = 0;
        for (long i = 1L; i <= N ; i++) {
            result += i;
        }
        return result;
    }

    @Benchmark
    public long sequentialRangeSum() {
        return LongStream.rangeClosed(1, N).reduce(0L, Long::sum);
    }

    @Benchmark
    public long parallelRangeSum() {
        return LongStream.rangeClosed(1, N).parallel().reduce(0L, Long::sum);
    }

    @TearDown(Level.Invocation)
    public void tearDown() {
        System.gc();
    }

}
