package org.treef.model.ziplist;

public class NodeLinkList<a> {
    private ZipListStrict<a> before;
    private a current;
    private ZipListStrict<a> after;

    public NodeLinkList(ZipListStrict<a> before, a current, ZipListStrict<a> after) {
        this.before = before;
        this.current = current;
        this.after = after;
    }
    public ZipListStrict<a> getBefore() {
        return before;
    }
    public void setBefore(ZipListStrict<a> before) {
        this.before = before;
    }
    public a getCurrent() {
        return current;
    }
    public void setCurrent(a current) {
        this.current = current;
    }
    public ZipListStrict<a> getAfter() {
        return after;
    }
    public void setAfter(ZipListStrict<a> after) {
        this.after = after;
    }
}