package org.robotframework.swing.table;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class InvalidCellExceptionSpec extends Specification<InvalidCellException> {
    public class Any {
        private String rowIdentifier = "123";
        private String columnIdentifier = "nonexistentColumn";

        public InvalidCellException create() {
            return new InvalidCellException(rowIdentifier, columnIdentifier);
        }

        public void errorMessageShouldDescribeThatCellDoesntExist() {
            String expectedErrorMessage = "The specified table cell (row: " + rowIdentifier + ", column: " + columnIdentifier + ") is invalid.";
            specify(context.getMessage(), should.equal(expectedErrorMessage));
        }
    }
}
