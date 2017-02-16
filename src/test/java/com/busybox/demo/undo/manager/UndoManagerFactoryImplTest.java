package com.busybox.demo.undo.manager;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.busybox.demo.undo.Document;
import com.busybox.demo.undo.UndoManager;
import com.busybox.demo.undo.UndoManagerFactory;

@RunWith(MockitoJUnitRunner.class)
public class UndoManagerFactoryImplTest {

    private final static Integer BUFFER_SIZE = new Integer(5);
    @Mock
    private Document document;
    private UndoManagerFactory factory = new UndoManagerFactoryImpl();

    @Test
    public void testCreateUndoManager() throws Exception {
        UndoManager manager = factory.createUndoManager(document, BUFFER_SIZE);
        assertNotNull(manager);
    }
}
