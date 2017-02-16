package com.busybox.demo.undo.document;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.busybox.demo.undo.Document;

/**
 * Created by vsoltys on 9/12/14.
 * <p/>
 * Simple document implementation
 */
public class DocumentImpl
    implements Document {

    public static final String ERROR_ILLEGAL_POSITION = "Illegal position at %s";
    public static final String ERROR_ILLEGAL_POSITION_DELETE = "The document has no changes to delete at position %s";
    public static final String ERROR_NO_SUCH_CHANGES = "There are no such changes at position %s";
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentImpl.class);
    /*
     * Implemented via StringBuilder to simplify case, better to use streams w/ offset etc.
     */
    private StringBuilder content;
    private int position;

    public DocumentImpl(CharSequence sequence) {
        this.content = new StringBuilder(sequence);
        this.position = content.length();
    }

    public void delete(int pos, String s) throws IllegalStateException {
        if (pos > content.length() || pos < 0) {
            LOGGER.error("Error deleting characters: incorrect position, %s", pos);
            throw new IllegalStateException(String.format(ERROR_ILLEGAL_POSITION_DELETE, pos));
        }

        int position = content.indexOf(s, pos);
        if (position > -1) {
            content.delete(pos, s.length());
        } else {
            LOGGER.error("Error deleting characters: no such sequence (%s) at position %s", s, pos);
            throw new IllegalStateException(String.format(ERROR_NO_SUCH_CHANGES, pos));
        }
    }

    public void insert(int pos, String s) throws IllegalStateException {
        if (pos > content.length() || pos < 0) {
            throw new IllegalStateException(String.format(ERROR_ILLEGAL_POSITION, pos));
        }
        content.insert(pos, s);
    }

    public void setDot(int pos) throws IllegalStateException {
        if (pos > content.length() || pos < 0) {
            throw new IllegalStateException(String.format(ERROR_ILLEGAL_POSITION, pos));
        }
        this.position = pos;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.content).append(this.position).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DocumentImpl other = (DocumentImpl) obj;
        return new EqualsBuilder().append(this.content, other.content).append(this.position, other.position).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
