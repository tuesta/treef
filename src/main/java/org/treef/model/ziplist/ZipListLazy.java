package org.treef.model.ziplist;

import org.treef.utils.adt.Maybe;

import java.util.function.Function;

public class ZipListLazy<b, a> implements ZipList<a> {
    private Maybe<b> state;
    private final ZipListStrict<a> zipListStrict;
    private final Function<b, Maybe<a>> generate;

    public ZipListLazy(b state, Function<b, Maybe<a>> generate) {
        this.state = new Maybe.Just<>(state);
        this.generate = generate;
        this.zipListStrict = new ZipListStrict<>();
    }

    public ZipListLazy() {
        this.state = new Maybe.Nothing<>();
        this.generate = null;
        this.zipListStrict = new ZipListStrict<>();

    }

    @Override
    public ZipList<a> empty() {
        return new ZipListLazy<>();
    }

    public Maybe<a> extract() {
        switch (this.zipListStrict.extract()) {
            case Maybe.Just<a> v -> { return v; }
            case Maybe.Nothing<a>() -> {
                if (this.state instanceof Maybe.Nothing) return new Maybe.Nothing<>();
                this.next();
                return this.extract();
            }
        }
    }

    public void next() {
        state.withJust(b -> {
            if (zipListStrict.hasNext()) zipListStrict.next();
            else {
                switch (this.generate.apply(b)) {
                    case Maybe.Nothing() -> this.state = new Maybe.Nothing<>();
                    case Maybe.Just(a a) -> this.zipListStrict.insertR(a);
                }
            }
            return null;
        });
    }

    public void prev() {
        state.withJust(b -> {
            this.zipListStrict.prev();
            return null;
        });
    }
}
