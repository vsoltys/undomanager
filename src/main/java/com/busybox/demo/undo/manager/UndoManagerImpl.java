package com.busybox.demo.undo.manager;

import java.util.ArrayDeque;
import java.util.Deque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.busybox.demo.undo.Change;
import com.busybox.demo.undo.Document;
import com.busybox.demo.undo.UndoManager;

/**
 * Created by vsoltys on 9/12/14.
 * <p/>
 * Implementation of UndoManager with two stacks of {code}Command{code}s (via Deque)
 */
public class UndoManagerImpl
    implements UndoManager {
    public static final String ERROR_NO_CHANGES_TO_UNDO = "There are no more changes to undo";
    public static final String ERROR_NO_CHANGES_TO_REDO = "There are no more changes to redo";
    private static final Logger LOGGER = LoggerFactory.getLogger(UndoManagerImpl.class);
    private Document document;
    private int bufferSize;

    private Deque<Change> undoChanges;
    private Deque<Change> redoChanges;

    public UndoManagerImpl(final Document document, final int bufferSize) {
        this.document = document;
        this.bufferSize = bufferSize;

        undoChanges = new ArrayDeque<Change>(bufferSize);
        redoChanges = new ArrayDeque<Change>(bufferSize);
    }

    public void registerChange(Change change) {
        if (change != null) {
            addUndoChange(change);
        }
    }

    public boolean canUndo() {
        return !undoChanges.isEmpty();
    }

    public void undo() throws IllegalStateException {
        if (this.canUndo()) {
            Change change = undoChanges.removeFirst();
            LOGGER.info(String.format("Applying undo operation for changes: %s", change));

            try {
                change.revert(getDocument());
            } catch (Exception e) {
                LOGGER.error("Error applying undo operation", e);
                throw new IllegalStateException(e);
            }

            addRedoChange(change);
        } else {
            LOGGER.error("Error applying redo operation: no changes to undo");
            throw new IllegalStateException(ERROR_NO_CHANGES_TO_UNDO);
        }
    }

    public boolean canRedo() {
        return !redoChanges.isEmpty();
    }

    public void redo() throws IllegalStateException {
        if (this.canRedo()) {
            Change change = redoChanges.removeFirst();
            LOGGER.info(String.format("Applying redo operation for changes: %s", change));

            try {
                change.apply(getDocument());
            } catch (Exception e) {
                LOGGER.error("Error applying redo operation", e);
                throw new IllegalStateException(e);
            }

            addUndoChange(change);
        } else {
            LOGGER.error("Error applying redo operation: no changes to redo");
            throw new IllegalStateException(ERROR_NO_CHANGES_TO_REDO);
        }
    }

    private void addUndoChange(Change change) {
        if (undoChanges.size() == this.getBufferSize()) {
            undoChanges.removeLast();
        }
        undoChanges.addFirst(change);
    }

    private void addRedoChange(Change change) {
        if (redoChanges.size() == this.getBufferSize()) {
            redoChanges.removeLast();
        }
        redoChanges.addFirst(change);
    }

    private Document getDocument() {
        return this.document;
    }

    private int getBufferSize() {
        return this.bufferSize;
    }
}
