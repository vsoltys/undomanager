package com.busybox.demo.undo.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.busybox.demo.undo.Document;
import com.busybox.demo.undo.UndoManager;
import com.busybox.demo.undo.UndoManagerFactory;

/**
 * Created by vsoltys on 9/12/14.
 * <p/>
 * Creates UndoManager for specified {code}Document{code} and {code}bufferSize{code}
 */
public class UndoManagerFactoryImpl
    implements UndoManagerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(UndoManagerFactoryImpl.class);

    public UndoManager createUndoManager(Document doc, int bufferSize) {
        LOGGER.info(String.format("Building new UndoManager with buffer size: %s", bufferSize));
        return new UndoManagerImpl(doc, bufferSize);
    }
}
