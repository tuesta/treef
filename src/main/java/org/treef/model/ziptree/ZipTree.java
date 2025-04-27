package org.treef.model.ziptree;

import org.treef.utils.adt.Maybe;

public interface ZipTree<a> {
    public ZipTree<a> empty();
    public Maybe<a> extract();
    public void prev();
    public void next();
    public void top();
    public void down();
}