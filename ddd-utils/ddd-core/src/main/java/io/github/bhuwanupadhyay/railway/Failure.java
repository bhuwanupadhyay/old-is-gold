package io.github.bhuwanupadhyay.railway;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings("WeakerAccess")
public class Failure<TSuccess, TFailure> extends Result<TSuccess, TFailure> {

    private final TFailure error;

    public Failure(final TFailure error) {
        this.error = error;
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    public TSuccess getValue() {
        throw new FailedResultHasNoValueException(getError());
    }

    @Override
    public TFailure getError() {
        return error;
    }

    @Override
    public String toString() {
        return "Result (" + "ServerError: " + getError() + ')';
    }

    @Override
    public Result<?, TFailure> combine(
            final Result<?, TFailure> otherResult) {
        return this;
    }

    @Override
    public <T> Result<T, TFailure> onSuccess(
            final Supplier<Result<T, TFailure>> function) {
        return new Failure<>(getError());
    }

    @Override
    public <T> Result<T, TFailure> onSuccess(
            final Supplier<T> function, final Class<T> clazz) {
        return new Failure<>(getError());
    }

    @Override
    public Result<TSuccess, TFailure> onSuccess(final Consumer<TSuccess> function) {
        return this;
    }

    @Override
    public Result<TSuccess, TFailure> ensure(
            final Predicate<TSuccess> predicate, final TFailure error) {
        return this;
    }

    @Override
    public Result<TSuccess, List<TFailure>> ensureAll(List<Ensure<TSuccess, TFailure>> ensure) {
        ArrayList<TFailure> failures = new ArrayList<>();
        failures.add(getError());
        return new Failure<>(failures);
    }

    @Override
    public <T> Result<T, TFailure> flatMap(
            final Function<TSuccess, Result<T, TFailure>> function) {
        return new Failure<>(getError());
    }

    @Override
    public <T> Result<T, TFailure> map(final Function<TSuccess, T> function) {
        return new Failure<>(getError());
    }

    @Override
    public <T> Result<T, TFailure> ifValueIsPresent(
            final Class<T> innerValue, final TFailure error) {
        return new Failure<>(getError());
    }

    @Override
    public Result<TSuccess, TFailure> onFailure(final Runnable function) {
        function.run();
        return this;
    }

    @Override
    public Result<TSuccess, TFailure> onFailureThrow(Function<TFailure, RuntimeException> function) {
        throw function.apply(getError());
    }

    @Override
    public Result<TSuccess, TFailure> onFailure(final Consumer<TFailure> function) {
        function.accept(getError());
        return this;
    }

    @Override
    public Result<TSuccess, TFailure> onFailure(
            final Predicate<TFailure> predicate, final Consumer<TFailure> function) {
        if (predicate.test(getError())) {
            function.accept(getError());
        }
        return this;
    }
}