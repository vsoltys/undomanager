package com.busybox.demo.undo.document;

import org.junit.Before;
import org.junit.Test;

import com.busybox.demo.undo.Document;

public class DocumentImplTest {

    private Document document = new DocumentImpl("test");

    @Before
    public void setup() {
        document = new DocumentImpl("Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
                + "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
    }

    @Test
    public void testDelete() throws Exception {
        document.delete(0, "Lorem");
    }

    @Test(expected = IllegalStateException.class)
    public void testDeletePositionFails() throws Exception {
        document.delete(23, "Lorem");
    }

    @Test(expected = IllegalStateException.class)
    public void testDeleteTextFails() throws Exception {
        document.delete(0, "text");
    }

    @Test
    public void testInsert() throws Exception {
        document.insert(1, "Lorem");
    }

    @Test(expected = IllegalStateException.class)
    public void testInsertFails() throws Exception {
        document.insert(999, "Lorem");
    }

    @Test
    public void testSetDot() throws Exception {
        document.setDot(23);
    }

    @Test(expected = IllegalStateException.class)
    public void testSetDotFails() throws Exception {
        document.setDot(999);
    }
}
