package com.busybox.demo.undo.change;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.busybox.demo.undo.Change;
import com.busybox.demo.undo.ChangeFactory;

public class ChangeFactoryImplTest {

    private ChangeFactory factory = new ChangeFactoryImpl();

    @Test
    public void testCreateDeletion() throws Exception {
        Change change = factory.createDeletion(1, "test", 2, 3);
        assertNotNull(change);
        assertEquals(ChangeTypeEnum.DELETE, ChangeTypeEnum.valueOf(change.getType()));
    }

    @Test
    public void testCreateInsertion() throws Exception {
        Change change = factory.createInsertion(1, "test", 2, 3);
        assertNotNull(change);
        assertEquals(ChangeTypeEnum.INSERT, ChangeTypeEnum.valueOf(change.getType()));
    }
}
