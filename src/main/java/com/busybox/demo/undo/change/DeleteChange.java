package com.busybox.demo.undo.change;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.busybox.demo.undo.Change;
import com.busybox.demo.undo.Document;

/**
 * Created by vsoltys on 9/12/14.
 * <p/>
 * Implemented w/ Command pattern via ChangeTypeEnum and ChangeFactory
 */
public class DeleteChange
    implements Change {

    private ChangeTypeEnum type = ChangeTypeEnum.DELETE;
    private String textChanges;
    private int startPosition;
    private int cursorPositionBefore;
    private int cursorPositionAfter;

    public DeleteChange(int startPosition, String textChanges, int cursorPositionBefore, int cursorPositionAfter) {
        this.textChanges = textChanges;
        this.startPosition = startPosition;
        this.cursorPositionBefore = cursorPositionBefore;
        this.cursorPositionAfter = cursorPositionAfter;
    }

    public String getType() {
        return type.name();
    }

    public void apply(Document document) throws IllegalStateException {
        if (document != null) {
            document.setDot(cursorPositionBefore);
            document.delete(startPosition, textChanges);
            document.setDot(cursorPositionAfter);
        }
    }

    public void revert(Document document) throws IllegalStateException {
        if (document != null) {
            document.setDot(cursorPositionAfter);
            document.insert(startPosition, textChanges);
            document.setDot(cursorPositionBefore);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(this.type)
            .append(this.textChanges)
            .append(this.startPosition)
            .append(this.cursorPositionBefore)
            .append(this.cursorPositionAfter)
            .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeleteChange other = (DeleteChange) obj;
        return new EqualsBuilder()
            .append(this.type, other.type)
            .append(this.textChanges, other.textChanges)
            .append(this.startPosition, other.startPosition)
            .append(this.cursorPositionBefore, other.cursorPositionBefore)
            .append(this.cursorPositionAfter, other.cursorPositionAfter)
            .isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
