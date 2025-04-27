package org.treef.model.ziplist;
import org.treef.utils.adt.Maybe;

public interface ZipList<a> {
    public ZipList<a> empty();
    public void next();
    public void prev();
    public Maybe<a> extract();
}