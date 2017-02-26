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
public class OptionalVsIfNull_String {
    @Param({"true", "false"})
    private boolean isNull;

    private String value;

    private String answerWhenNull = "NULL";

    @Setup(Level.Trial)
    public void setUp() {
        value = isNull ? null : "not null";
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
        return answerWhenNull;
    }

    @Benchmark
    public String optionalWithExplicitGet() {
        Optional<String> optional = Optional.ofNullable(value);
        if (optional.isPresent()) {
            return optional.get();
        }
        return answerWhenNull;
    }

    @Benchmark
    public String optionalWithOrElse() {
        return Optional.ofNullable(value).orElse(answerWhenNull);
    }

    @Benchmark
    public String optionalWithOrElseGet() {
        return Optional.ofNullable(value).orElseGet(() -> answerWhenNull);
    }
}