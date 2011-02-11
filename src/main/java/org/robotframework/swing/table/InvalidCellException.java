package org.robotframework.swing.table;

public class InvalidCellException extends RuntimeException {
    private final int row;
    private final String columnIdentifier;

    public InvalidCellException(int row, String columnIdentifier) {
        this.row = row;
        this.columnIdentifier = columnIdentifier;
    }

    @Override
    public String getMessage() {
        return "The specified table cell (row: " + row + ", column: " + columnIdentifier + ") is invalid.";
    }
}
