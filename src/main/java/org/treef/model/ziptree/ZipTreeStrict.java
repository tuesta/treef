package org.treef.model.ziptree;

import org.treef.model.ziplist.ZipListStrict;
import org.treef.utils.adt.Maybe;
import org.treef.utils.adt.T;

public class ZipTreeStrict<a> /*implements ZipTree<a>*/ {
    /*
    private Maybe<T<a, ZipTreeStrict<a>>> father;
    private Maybe<T<a, ZipTreeStrict<a>>> children;
    private ZipListStrict<T<a, ZipTreeStrict<a>>> brothers;

    public ZipTreeStrict(Maybe<T<a, ZipTreeStrict<a>>> father, Maybe<T<a, ZipTreeStrict<a>>> children, ZipListStrict<T<a, ZipTreeStrict<a>>> brothers) {
        this.father = father;
        this.children = children;
        this.brothers = brothers;
    }

    public ZipTreeStrict() {
        this.father = new Maybe.Nothing<>();
        this.children = new Maybe.Nothing<>();
        this.brothers = new ZipListStrict<>();
    }

    @Override
    public Maybe<a> extract() {
        return switch (brothers.extract()) {
            case Maybe.Nothing() -> new Maybe.Nothing<>();
            case Maybe.Just(T<a, ZipTreeStrict<a>> current) -> new Maybe.Just<>(current.fst());
        };
    }

    private boolean currentChildren() {
        switch (this.brothers.extract()) {
            case Maybe.Nothing() -> { return false; }
            case Maybe.Just(T<a, ZipTreeStrict<a>> current) -> {
                this.children = current.snd().children;
                return true;
            }
        }
    }

    @Override
    public boolean prev() {
        return this.brothers.prev() && currentChildren();
    }

    @Override
    public boolean next() {
        return this.brothers.next() && currentChildren();
    }

    @Override
    public boolean top() {
        switch (this.father) {
            case Maybe.Nothing() -> { return false; }
            case Maybe.Just(T<a, ZipTreeStrict<a>> father) -> {
                this.children = father.snd().children;
                this.father = father.snd().father;
                this.brothers = father.snd().brothers;
                return true;
            }
        }
    }

    @Override
    public boolean down() {
        switch (this.children) {
            case Maybe.Nothing() -> { return false; }
            case Maybe.Just(T<a, ZipTreeStrict<a>> children) -> {
                this.father = children.snd().father;
                this.brothers = children.snd().brothers;
                this.children = children.snd().children;
                return true;
            }
        }
    }

    public void insertR(a val) {
        this.brothers.insertR(new T.MkT<>(val, new ZipTreeStrict<>(this.father, new Maybe.Nothing<>(), this.brothers)));
    }
    */
}