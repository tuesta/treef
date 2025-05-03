package org.treef.model.ziptree;

import org.treef.model.ziplist.ZipListInc;
import org.treef.utils.adt.Maybe;

public class TreeF<b> {
    private Maybe<TreeF<b>> father;
    private ZipListInc<b, TreeF<b>> brothers;
    private ZipListInc<b, TreeF<b>> children;

    public TreeF(Maybe<TreeF<b>> father, ZipListInc<b, TreeF<b>> brothers, ZipListInc<b, TreeF<b>> children) {
        this.father = father;
        this.brothers = brothers;
        this.children = children;
    }

    public Maybe<TreeF<b>> getFather() { return father; }

    public void setFather(Maybe<TreeF<b>> father)   { this.father = father; }

    public ZipListInc<b, TreeF<b>> getBrothers() { return brothers; }

    public void setBrothers(ZipListInc<b, TreeF<b>> brothers) { this.brothers = brothers; }

    public ZipListInc<b, TreeF<b>> getChildren() { return children; }

    public void setChildren(ZipListInc<b, TreeF<b>> children) { this.children = children; }
}