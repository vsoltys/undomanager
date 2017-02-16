package com.busybox.demo.undo.change;

/**
 * Created by vsoltys on 9/12/14.
 * <p/>
 * Defines set of commands used in UndoManager
 */
public enum ChangeTypeEnum {

    INSERT("Add changes"),
    DELETE("Remove changes");

    private final String description;

    ChangeTypeEnum(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
