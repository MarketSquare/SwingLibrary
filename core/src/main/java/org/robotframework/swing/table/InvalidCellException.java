package org.robotframework.swing.table;

/**
 * @author Heikki Hulkko
 */
public class InvalidCellException extends RuntimeException {
    private static final long serialVersionUID = -6008349670285895563L;
    private final String rowIdentifier;
    private final String columnIdentifier;

    public InvalidCellException(String rowIdentifier, String columnIdentifier) {
        this.rowIdentifier = rowIdentifier;
        this.columnIdentifier = columnIdentifier;
    }

    @Override
    public String getMessage() {
        return "Table cell (row: " + rowIdentifier + ", column: " + columnIdentifier + ") doesn't exist.";
    }
}
