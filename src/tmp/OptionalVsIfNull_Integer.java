package sample;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class OptionalVsIfNull_Integer {
    @Param({"true", "false"})
    private boolean isNull;
    private final Integer ONE = Integer.valueOf(1);
    private final Integer ZERO = Integer.valueOf(0);

    private Integer value;

    @Setup(Level.Trial)
    public void setUp() {
        value = isNull ? null : ONE;
    }

    @Benchmark
    public Integer baseline() {
        return value;
    }

    @Benchmark
    public Integer ifNull() {
        Integer val = value;
        if (val != null) {
            return val;
        }
        return ZERO;
    }

    @Benchmark
    public Integer optionalWithOrElse() {
        return Optional.ofNullable(value).orElse(ZERO);
    }

    @Benchmark
    public Integer optionalWithOrElseGet() {
        return Optional.ofNullable(value).orElseGet(() -> ZERO);
    }

    @Benchmark
    public Integer optionalWithExplicitGet() {
        Optional<Integer> optional = Optional.ofNullable(value);
        if (optional.isPresent()) {
            return optional.get();
        }
        return ZERO;
    }
}