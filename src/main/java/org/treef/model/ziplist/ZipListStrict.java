package org.treef.model.ziplist;

import org.treef.utils.adt.Maybe;

public class ZipListStrict<a> implements ZipList<a>{
    private Maybe<NodeLinkList<a>> mNode;

    public Maybe<NodeLinkList<a>> getMNode() {
        return this.mNode;
    }

    @Override
    public void setCurrent(a a) {
        switch (this.mNode) {
            case Maybe.Nothing() -> {}
            case Maybe.Just(NodeLinkList<a> node) -> { node.setCurrent(a); }
        }
    }

    public ZipListStrict(Maybe<NodeLinkList<a>> mNode) { this.mNode = mNode; }

    public ZipListStrict() { this.mNode = new Maybe.Nothing<>(); }

    @Override
    public Maybe<a> extract() {
        return switch (this.mNode) {
            case Maybe.Nothing() -> new Maybe.Nothing<>();
            case Maybe.Just(NodeLinkList<a> node) -> new Maybe.Just<>(node.getCurrent());
        };
    }

    @Override
    public <x> ZipList<x> empty() {
        return new ZipListStrict<>();
    }

    public boolean hasNext() {
        return switch (this.mNode) {
            case Maybe.Nothing() -> false;
            case Maybe.Just(NodeLinkList<a> node) ->
                    switch (node.getAfter().mNode) {
                        case Maybe.Nothing() -> false;
                        case Maybe.Just(NodeLinkList<a> afterNode) -> true;
                    };
        };
    }

    @Override
    public boolean next() {
        switch (this.mNode) {
            case Maybe.Nothing() -> { return false; }
            case Maybe.Just(NodeLinkList<a> node) -> {
                switch (node.getAfter().mNode) {
                    case Maybe.Nothing() -> { return false; }
                    case Maybe.Just(NodeLinkList<a> nodeAfter) -> {
                        var currentNode = new Maybe.Just<NodeLinkList<a>>(new NodeLinkList<a>(node.getBefore(), node.getCurrent(), this));
                        var currentList = new ZipListStrict<>(currentNode);
                        nodeAfter.setBefore(currentList);
                        this.mNode = node.getAfter().mNode;
                        return true;
                    }
                }
            }
        }
    }

    @Override
    public boolean prev() {
        switch (this.mNode) {
            case Maybe.Nothing() -> { return false; }
            case Maybe.Just(NodeLinkList<a> node) -> {
                switch (node.getBefore().mNode) {
                    case Maybe.Nothing() -> { return false; }
                    case Maybe.Just(NodeLinkList<a> nodeBefore) -> {
                        var currentNode = new Maybe.Just<>(new NodeLinkList<a>(this, node.getCurrent(), node.getAfter()));
                        var currentList = new ZipListStrict<>(currentNode);
                        nodeBefore.setAfter(currentList);
                        this.mNode = node.getBefore().mNode;
                        return true;
                    }
                }
            }
        }
    }

    private Maybe<NodeLinkList<a>> singletonNode(a val) {
        return new Maybe.Just<>(new NodeLinkList<>(new ZipListStrict<>(), val, new ZipListStrict<>()));
    }

    public void insertR(a val) {
        switch (this.mNode) {
            case Maybe.Nothing() -> {
                this.mNode = singletonNode(val);
            }
            case Maybe.Just(NodeLinkList<a> node) -> {
                var currentNode = new Maybe.Just<>(new NodeLinkList<>(node.getBefore(), node.getCurrent(), this));
                var currentList = new ZipListStrict<>(currentNode);

                node.setCurrent(val);
                node.setBefore(currentList);
            }
        };
    }

    public void insertL(a val) {
        switch (this.mNode) {
            case Maybe.Nothing() -> {
                this.mNode = singletonNode(val);
            }
            case Maybe.Just(NodeLinkList<a> node) -> {
                var currentNode = new Maybe.Just<>(new NodeLinkList<>(this, val, node.getAfter()));
                var currenList = new ZipListStrict<>(currentNode);

                node.setCurrent(val);
                node.setAfter(currenList);
            }
        }
    }

    @Override
    public void show() {
        switch (this.mNode) {
            case Maybe.Nothing() -> { System.out.println("--"); }
            case Maybe.Just(NodeLinkList<a> node) -> {
                System.out.print(node.getBefore().showLeft());
                System.out.print("@" + ">" + node.getCurrent() + "<" + "@");
                System.out.println(node.getAfter().showRight());
            }
        };
    }

    public String showLeft() {
        return switch (this.mNode) {
            case Maybe.Nothing() -> "||";
            case Maybe.Just(NodeLinkList<a> node) -> node.getBefore().showLeft() + "@"+ node.getCurrent().toString();
        };
    }
    public String showRight() {
        return switch (this.mNode) {
            case Maybe.Nothing() -> "||";
            case Maybe.Just(NodeLinkList<a> node) -> node.getCurrent().toString() +"@"+ node.getAfter().showRight();
        };
    }
}