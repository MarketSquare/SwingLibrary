package org.robotframework.swing.table;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class InvalidCellExceptionSpec extends Specification<InvalidCellException> {
    public class Any {
        private int row = 123;
        private String columnIdentifier = "nonexistentColumn";

        public InvalidCellException create() {
            return new InvalidCellException(row, columnIdentifier);
        }

        public void errorMessageShouldDescribeThatCellDoesntExist() {
            String expectedErrorMessage = "The specified table cell (row: " + row + ", column: " + columnIdentifier + ") is invalid.";
            specify(context.getMessage(), should.equal(expectedErrorMessage));
        }
    }
}
