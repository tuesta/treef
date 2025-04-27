package org.treef.model.ziplist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZipListStrictTest {
    private ZipListStrict<Integer> list;

    @BeforeEach
    void setUp() {
        this.list = new ZipListStrict<>();
    }

    @Test
    void showLeft() {
        assertEquals("||", list.showLeft());
    }

    @Test
    void showRight() {
        assertEquals("||", list.showRight());
    }
}