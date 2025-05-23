package org.treef.model.ziptree;

import org.treef.utils.adt.Maybe;

public class ZipTreeStrict<a> implements ZipTree<a> {
    private NodeLinkTree<a> node;

    public ZipTreeStrict(NodeLinkTree<a> node) { this.node = node; }

    @Override
    public a extract() { return node.getCurrent(); }

    @Override
    public boolean prev() {
        switch (this.node.getBefore()) {
            case Maybe.Nothing() -> { return false; }
            case Maybe.Just(NodeLinkTree<a> beforeNode) -> { this.node = beforeNode; return true; }
        }
    }

    @Override
    public boolean next() {
        switch (this.node.getAfter()) {
            case Maybe.Nothing() -> { return false; }
            case Maybe.Just(NodeLinkTree<a> afterNode) -> { this.node = afterNode; return true; }
        }
    }

    @Override
    public boolean top() {
        switch (node.getUp()) {
            case Maybe.Nothing() -> { return false; }
            case Maybe.Just(NodeLinkTree<a> upNode) -> { this.node = upNode; return true; }
        }
    }

    @Override
    public boolean down() {
        switch (node.getDown()) {
            case Maybe.Nothing() -> { return false; }
            case Maybe.Just(NodeLinkTree<a> downNode) -> { this.node = downNode; return true; }
        }
    }

    public void insertR(a val) {
        var cNode = this.node;
        var newNode = new NodeLinkTree<>(val, new Maybe.Just<>(cNode), cNode.getAfter(),cNode.getUp(), new Maybe.Nothing<>());
        cNode.setAfter(new Maybe.Just<>(newNode));
        this.node = newNode;
    }

    public void insertD(a val) {
        NodeLinkTree<a> newNode;
        switch (this.node.getDown()) {
            case Maybe.Nothing() ->
                newNode = new NodeLinkTree<>(val, new Maybe.Nothing<>(), new Maybe.Nothing<>(), new Maybe.Just<>(this.node), new Maybe.Nothing<>());
            case Maybe.Just(NodeLinkTree<a> downNode) -> {
                newNode = new NodeLinkTree<>(val, new Maybe.Just<>(downNode), downNode.getAfter(), new Maybe.Just<>(this.node), new Maybe.Nothing<>());
                downNode.setAfter(new Maybe.Just<>(newNode));
            }
        }

        this.node.setDown(new Maybe.Just<>(newNode));
        this.node = newNode;
    }

    public void show() { this.node.show(); }
}
