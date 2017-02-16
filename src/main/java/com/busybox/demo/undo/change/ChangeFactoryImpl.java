package com.busybox.demo.undo.change;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.busybox.demo.undo.Change;
import com.busybox.demo.undo.ChangeFactory;

/**
 * Created by vsoltys on 9/12/14.
 * <p/>
 * Creates one of possible commands basing on operation (i.e. insertion/deletion)
 */
public class ChangeFactoryImpl
    implements ChangeFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeFactoryImpl.class);

    public Change createDeletion(int pos, String s, int oldDot, int newDot) {
        LOGGER.info(String.format("Building new DeleteChange with pos: %s, text: %s", pos, s));
        return new DeleteChange(pos, s, oldDot, newDot);
    }

    public Change createInsertion(int pos, String s, int oldDot, int newDot) {
        LOGGER.info(String.format("Building new InsertChange with pos: %s, text: %s", pos, s));
        return new InsertChange(pos, s, oldDot, newDot);
    }
}
