package sample;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class OptionalVsIfNull_AlwaysNonNull {
    private String value = "something";
    private String valueWhenNull = "NULL";

    private String compute() {
        return valueWhenNull;
    }

    @Benchmark
    public String baseline() {
        return value;
    }

    @Benchmark
    public String ifNull() {
        String val = value;
        if (val != null) {
            return val;
        }
        return compute();
    }

    @Benchmark
    public String optionalWithExplicitGet() {
        String val = value;
        Optional<String> optional = Optional.ofNullable(val);
        if (optional.isPresent()) {
            return optional.get();
        }
        return compute();
    }

    @Benchmark
    public String optionalWithOrElse() {
        String val = value;
        return Optional.ofNullable(val).orElse(compute());
    }

    @Benchmark
    public String optionalWithOrElseGet() {
        String val = value;
        return Optional.ofNullable(val).orElseGet(this::compute);
    }
}