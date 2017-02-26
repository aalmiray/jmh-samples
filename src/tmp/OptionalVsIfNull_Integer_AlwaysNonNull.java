package sample;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class OptionalVsIfNull_Integer_AlwaysNonNull {
    private final AtomicInteger counter = new AtomicInteger(1);
    private final Integer ZERO = Integer.valueOf(0);

    private Integer value() {
        int i = counter.get();
        return i % 2 == 0 ? null : i;
    }

    @Benchmark
    public Integer baseline() {
        return value();
    }

    @Benchmark
    public Integer ifNull() {
        Integer value = value();
        if (value != null) {
            return value;
        }
        return ZERO;
    }

    @Benchmark
    public Integer optionalWithOrElse() {
        return Optional.ofNullable(value()).orElse(ZERO);
    }

    @Benchmark
    public Integer optionalWithOrElseGet() {
        return Optional.ofNullable(value()).orElseGet(() -> ZERO);
    }

    @Benchmark
    public Integer optionalWithExplicitGet() {
        Optional<Integer> optional = Optional.ofNullable(value());
        if (optional.isPresent()) {
            return optional.get();
        }
        return ZERO;
    }
}