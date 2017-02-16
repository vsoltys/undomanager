package com.busybox.demo.undo.change;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.InOrder;

import com.busybox.demo.undo.Change;
import com.busybox.demo.undo.Document;

public class InsertChangeTest {

    private Change change = new InsertChange(1, "text", 2, 3);

    @Test
    public void testGetType() throws Exception {
        assertEquals(ChangeTypeEnum.INSERT, ChangeTypeEnum.valueOf(change.getType()));
    }

    @Test
    public void testApply() throws Exception {
        Document document = mock(Document.class);

        change.apply(document);

        InOrder order = inOrder(document);
        order.verify(document).setDot(anyInt());
        order.verify(document).insert(anyInt(), anyString());
        order.verify(document).setDot(anyInt());
    }

    @Test
    public void testRevert() throws Exception {
        Document document = mock(Document.class);

        change.revert(document);

        InOrder order = inOrder(document);
        order.verify(document).setDot(anyInt());
        order.verify(document).delete(anyInt(), anyString());
        order.verify(document).setDot(anyInt());
    }
}
