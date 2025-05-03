package org.treef.model.ziplist;

import java.util.function.Function;

public interface ZipListMap<a, b> extends ZipList<b> {
    public void map(Function<a, b> f);
}