package org.robotframework.swing.table;

/**
 * @author Heikki Hulkko
 */
public class InvalidCellException extends RuntimeException {
    private final String rowIdentifier;
    private final String columnIdentifier;

    public InvalidCellException(String rowIdentifier, String columnIdentifier) {
        this.rowIdentifier = rowIdentifier;
        this.columnIdentifier = columnIdentifier;
    }

    @Override
    public String getMessage() {
        return "The specified table cell (row: " + rowIdentifier + ", column: " + columnIdentifier + ") is invalid.";
    }
}
