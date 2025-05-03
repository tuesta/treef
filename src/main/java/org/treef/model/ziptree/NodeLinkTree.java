package org.treef.model.ziptree;

import org.treef.utils.Mut;
import org.treef.utils.adt.Maybe;

import java.util.LinkedList;

public class NodeLinkTree<a> implements ZipTree<a> {
    private a current;
    private Maybe<NodeLinkTree<a>> before;
    private Maybe<NodeLinkTree<a>> after;
    private Maybe<NodeLinkTree<a>> up;
    private Maybe<NodeLinkTree<a>> down;

    public NodeLinkTree(a current, Maybe<NodeLinkTree<a>> before, Maybe<NodeLinkTree<a>> after, Maybe<NodeLinkTree<a>> up, Maybe<NodeLinkTree<a>> down) {
        this.current = current;
        this.before = before;
        this.after = after;
        this.up = up;
        this.down = down;
    }

    @Override
    public a extract() { return current; }

    @Override
    public boolean prev() {
        switch (this.before) {
            case Maybe.Nothing() -> { return false; }
            case Maybe.Just(NodeLinkTree<a> beforeNode) -> {
                NodeLinkTree<a> cNode= new NodeLinkTree<>(this.current, new Maybe.Just<>(this), this.after, this.up, this.down);
                this.after = new Maybe.Just<>(cNode);
                this.before = beforeNode.before;
                this.current = beforeNode.current;
                this.up = beforeNode.up;
                this.down = beforeNode.down;
                return true;
            }
        }
    }

    @Override
    public boolean next() {
        switch (this.after) {
            case Maybe.Nothing() -> { return false; }
            case Maybe.Just(NodeLinkTree<a> afterNode) -> {
                NodeLinkTree<a> cNode= new NodeLinkTree<>(this.current, this.before,new Maybe.Just<>(this), this.up, this.down);
                this.before = new Maybe.Just<>(cNode);
                this.after = afterNode.after;
                this.current = afterNode.current;
                this.up = afterNode.up;
                this.down = afterNode.down;
                return true;
            }
        }
    }

    @Override
    public boolean top() {
        switch (this.up) {
            case Maybe.Nothing() -> { return false; }
            case Maybe.Just(NodeLinkTree<a> upNode) -> {
                NodeLinkTree<a> cNode = new NodeLinkTree<>(this.current, this.before, this.after, new Maybe.Just<>(this), this.down);
                this.down = new Maybe.Just<>(cNode);
                this.up = upNode.up;
                this.before = upNode.before;
                this.after = upNode.after;
                this.current = upNode.current;
                return true;
            }
        }
    }

    @Override
    public boolean down() {
        switch (this.down) {
            case Maybe.Nothing() -> { return false; }
            case Maybe.Just(NodeLinkTree<a> downNode) -> {
                NodeLinkTree<a> cNode = new NodeLinkTree<>(this.current, this.before, this.after, this.up, new Maybe.Just<>(this));
                this.up = new Maybe.Just<>(cNode);
                this.down = downNode.down;
                this.before = downNode.before;
                this.after = downNode.after;
                this.current = downNode.current;
                return true;
            }
        }
    }

    public void insertR(a val) {
        NodeLinkTree<a> cNode = new NodeLinkTree<>(this.current, this.before, new Maybe.Just<>(this), this.up, this.down);
        this.current = val;
        this.before = new Maybe.Just<>(cNode);
        this.down = new Maybe.Nothing<>();
    }

    public void insertD(a val) {
        NodeLinkTree<a> cNode = new NodeLinkTree<>(this.current, this.before, this.after, this.up, new Maybe.Just<>(this));
        this.current = val;
        this.up = new Maybe.Just<>(cNode);
        switch (this.down) {
            case Maybe.Nothing() -> {
                this.after = new Maybe.Nothing<>();
                this.before= new Maybe.Nothing<>();
            }
            case Maybe.Just(NodeLinkTree<a> downNode) -> {
                this.after = downNode.after;
                downNode.after = new Maybe.Just<>(this);
            }
        }
        this.down = new Maybe.Nothing<>();
    }

    public String unlines(LinkedList<Mut<String>> xs) {
        return xs.stream()
                .map(mut -> mut.val)
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
    }

    public String toString() {
        return unlines(draw(this));
    }

    public LinkedList<Mut<String>> draw(NodeLinkTree<a> node) {
        return drawUp(drawBoth(new Maybe.Just<>(node), true), node.up);
    }

    public LinkedList<Mut<String>> drawUp(LinkedList<Mut<String>> children, Maybe<NodeLinkTree<a>> mnode) {
        switch (mnode) {
            case Maybe.Nothing() -> { return children; }
            case Maybe.Just(NodeLinkTree<a> upNode) -> {
                LinkedList<Mut<String>> level = drawLeft(upNode.before);

                LinkedList<Mut<String>> current = new LinkedList<>();
                current.add(new Mut<>(upNode.current.toString()));
                current.addAll(children);
                if (upNode.up.isJust()) {
                    if (upNode.after.isNothing()) shift(current, "`- ", "   ");
                    else shift(current, "+- ", "|  ");
                    current.addFirst(new Mut<>("|"));
                }

                level.addAll(current);
                level.addAll(drawRight(upNode.after, true, false));

                return drawUp(level, upNode.up);
            }
        }
    }

    public LinkedList<Mut<String>> drawBoth(Maybe<NodeLinkTree<a>> mnode, boolean isCurrent) {
        LinkedList<Mut<String>> both;
        switch (mnode) {
            case Maybe.Nothing() -> {both = new LinkedList<>(); }
            case Maybe.Just(NodeLinkTree<a> node) -> {
                both = drawLeft(node.before);
                if (node.up.isJust()) both.add(new Mut<>("|"));
                both.addAll(drawRight(mnode, true, isCurrent));
            }
        }
        return both;
    }

    public LinkedList<Mut<String>> drawLeft(Maybe<NodeLinkTree<a>> mnode) {
        LinkedList<Mut<String>> left;
        switch (mnode) {
            case Maybe.Nothing() -> { left = new LinkedList<>(); }
            case Maybe.Just(NodeLinkTree<a> nodeLeft) -> {
                left = drawLeft(nodeLeft.before);

                LinkedList<Mut<String>> children = new LinkedList<>();
                children.add(new Mut<>(nodeLeft.current.toString()));
                children.addAll(drawBoth(nodeLeft.down, false));
                if (nodeLeft.up.isJust()) {
                    shift(children, "`+ ", "|  ");
                    children.addFirst(new Mut<>("|"));
                }

                left.addAll(children);
            }
        }
        return left;
    }

    public LinkedList<Mut<String>> drawRight(Maybe<NodeLinkTree<a>> mnode, boolean isFromBoth, boolean isCurrent) {
        LinkedList<Mut<String>> right;
        switch (mnode) {
            case Maybe.Nothing() -> { right = new LinkedList<>(); }
            case Maybe.Just(NodeLinkTree<a> nodeRight) -> {
                LinkedList<Mut<String>> children = new LinkedList<>();
                String currentStr = nodeRight.current.toString();
                if (isCurrent) {
                    currentStr = ">>" + currentStr + "<<";
                }
                children.add(new Mut<>(currentStr));
                children.addAll(drawBoth(nodeRight.down, false));
                if (nodeRight.up.isJust()) {
                    if (nodeRight.after.isNothing()) shift(children, "`- ", "   ");
                    else shift(children, "+- ", "|  ");
                }

                if (!isFromBoth) children.addFirst(new Mut<>("|"));

                right = children;
                right.addAll(drawRight(nodeRight.after, false, false));
            }
        }
        return right;
    }

    public void shift(LinkedList<Mut<String>> xs, String first, String other) {
        boolean isFirst = true;
        for (Mut<String> str : xs) {
            String prefix = isFirst ? first : other;
            isFirst = false;
            str.val = prefix + str.val;
        }
    }
}