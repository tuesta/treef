package org.treef.utils.adt;

import java.util.function.Function;

sealed public interface Maybe<a> {
    public a fromJust();
    public void withJust(Function<a, Void> with);
    public boolean isNothing();
    public boolean isJust();

    public record Nothing<a>() implements Maybe<a> {
        @Override
        public a fromJust() {
            return null;
        }
        @Override
        public void withJust(Function<a, Void> with) {}
        @Override
        public boolean isNothing() { return true; }
        @Override
        public boolean isJust() { return false; }
    };
    public record Just<a>(a value) implements Maybe<a> {
        @Override
        public a fromJust() {
            return value;
        }
        @Override
        public void withJust(Function<a, Void> with) {
            with.apply(value);
        }
        @Override
        public boolean isNothing() { return false; }
        @Override
        public boolean isJust() { return true; }
    };
}