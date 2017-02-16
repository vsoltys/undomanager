package com.busybox.demo.undo.manager;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.busybox.demo.undo.ChangeFactory;
import com.busybox.demo.undo.Document;
import com.busybox.demo.undo.UndoManager;
import com.busybox.demo.undo.change.DeleteChange;
import com.busybox.demo.undo.change.InsertChange;

@RunWith(MockitoJUnitRunner.class)
public class UndoManagerImplTest {

    private static final Integer BUFFER_SIZE = new Integer(3);
    @Mock
    private Document document;
    @InjectMocks
    private UndoManager manager = new UndoManagerImpl(document, BUFFER_SIZE);
    @Mock
    private ChangeFactory factory;

    @Before
    public void setupMocks() {

        when(factory.createInsertion(anyInt(), anyString(), anyInt(), anyInt()))
            .thenReturn(new InsertChange(1, "xxx", 2, 3));

        when(factory.createDeletion(anyInt(), anyString(), anyInt(), anyInt()))
            .thenReturn(new DeleteChange(1, "yyy", 2, 3));
    }

    @Test
    public void testRegisterChange() throws Exception {
        manager.registerChange(factory.createInsertion(1, "", 1, 1));
        assertTrue(manager.canUndo());
        manager.undo();
        assertFalse(manager.canUndo());
    }

    @Test
    public void testCanUndo() throws Exception {
        assertFalse(manager.canUndo());
        manager.registerChange(factory.createInsertion(1, "", 1, 1));
        assertTrue(manager.canUndo());
        manager.undo();
        assertFalse(manager.canUndo());
    }

    @Test
    public void testUndo() throws Exception {
        assertFalse(manager.canUndo());
        manager.registerChange(factory.createDeletion(1, "", 1, 1));
        assertTrue(manager.canUndo());
        manager.undo();
        assertFalse(manager.canUndo());
        manager.redo();
        assertTrue(manager.canUndo());
    }

    @Test(expected = IllegalStateException.class)
    public void testNoMoreChangesToUndoError() throws Exception {
        manager.registerChange(factory.createDeletion(1, "", 1, 1));
        manager.undo();
        manager.undo();
    }

    @Test
    public void testCanRedo() throws Exception {
        assertFalse(manager.canRedo());
        manager.registerChange(factory.createInsertion(1, "", 1, 1));
        assertFalse(manager.canRedo());

        manager.undo();
        assertTrue(manager.canRedo());

        manager.redo();
        assertFalse(manager.canRedo());
    }

    @Test
    public void testRedo() throws Exception {
        manager.registerChange(factory.createDeletion(1, "", 1, 1));
        manager.undo();
        assertTrue(manager.canRedo());
        manager.redo();
        assertTrue(manager.canUndo());
        manager.undo();
        assertTrue(manager.canRedo());
        manager.redo();
        assertTrue(manager.canUndo());
    }

    @Test(expected = IllegalStateException.class)
    public void testNoMoreChangesToRedoError() throws Exception {
        manager.registerChange(factory.createDeletion(1, "", 1, 1));
        assertTrue(manager.canUndo());
        manager.undo();
        assertTrue(manager.canRedo());
        manager.redo();
        assertTrue(manager.canUndo());
        assertFalse(manager.canRedo());
        manager.redo();
    }

    @Test(expected = IllegalStateException.class)
    public void testBufferSizeError() throws Exception {
        for (int index = 0; index < 4; index++) {
            manager.registerChange(factory.createInsertion(1, "", 1, 1));
        }

        for (int index = 0; index < 4; index++) {
            manager.undo();
        }
    }
}
