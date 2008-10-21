package org.robotframework.swing.keyword.table;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.table.EnhancedTableOperator;


@RunWith(JDaveRunner.class)
public class TableKeywordsSpec extends MockSupportSpecification<TableKeywords> {
    private String tableIdentifier = "someTable";
    private EnhancedTableOperator tableOperator;
    private String columnIdentifier = "two";
    private String row = "1";

    public class Any {
        public TableKeywords create() {
            return new TableKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasSelectTableCellKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectTableCell")));
        }

        public void hasClearTableSelectionKeyword() {
            specify(context, satisfies(new RobotKeywordContract("clearTableSelection")));
        }

        public void hasTableCellShouldBeSelectedKeyword() {
            specify(context, satisfies(new RobotKeywordContract("tableCellShouldBeSelected")));
        }

        public void hasTableCellShouldNotBeSelectedKeyword() {
            specify(context, satisfies(new RobotKeywordContract("tableCellShouldNotBeSelected")));
        }

        public void hasGetTableCellValueKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getTableCellValue")));
        }

        public void hasGetSelectedTableCellValueKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getSelectedTableCellValue")));
        }

        public void hasSetTableCellValueKeyword() {
            specify(context, satisfies(new RobotKeywordContract("setTableCellValue")));
        }

        public void hasGetTableColumnCountKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getTableColumnCount")));
        }

        public void hasGetTableRowCountKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getTableRowCount")));
        }

        public void hasOperatorFactory() {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }
    }

    public class OperatingOnTable {
        private OperatorFactory operatorFactory;
        private IContextVerifier contextVerifier;
        private TableKeywords tableKeywords = new TableKeywords();

        public TableKeywords create() {
            injectMockOperatorFactory();
            injectMockContextVerifier();
            return tableKeywords;
        }
        
        public void getsTableColumnCount() {
            checking(new Expectations() {{
                one(tableOperator).getColumnCount(); will(returnValue(7));
            }});

            specify(context.getTableColumnCount(tableIdentifier), must.equal(7));
        }

        public void getsTableRowCount() {
            checking(new Expectations() {{
                one(tableOperator).getRowCount(); will(returnValue(7));
            }});

            specify(context.getTableRowCount(tableIdentifier), must.equal(7));
        }

        public void setsTableCellValue() {
            final String newValue = "newValue";

            checking(new Expectations() {{
                one(tableOperator).setValueAt(newValue, row, columnIdentifier);
            }});

            context.setTableCellValue(tableIdentifier, row, columnIdentifier, newValue);
        }
        
        private void injectMockContextVerifier() {
            contextVerifier = injectMockTo(tableKeywords, IContextVerifier.class);

            checking(new Expectations() {{
                one(contextVerifier).verifyContext();
            }});
        }

        private void injectMockOperatorFactory() {
            operatorFactory = injectMockTo(tableKeywords, OperatorFactory.class);
            tableOperator = mock(EnhancedTableOperator.class);

            checking(new Expectations() {{
                one(operatorFactory).createOperator(tableIdentifier);
                will(returnValue(tableOperator));
            }});
        }
    }

    public class SelectingTableCells {
        public TableKeywords create() {
            setMockTableOperatorAsContext();
            return createTableKeywordsWithMockContextVerifier();
        }

        public void selectsTableCell() {
            checking(new Expectations() {{
                one(tableOperator).selectCell(row, columnIdentifier);
            }});

            context.selectTableCell(row, columnIdentifier);
        }

        public void clearsTableSelection() {
            checking(new Expectations() {{
                one(tableOperator).clearSelection();
            }});

            context.clearTableSelection();
        }

        public void tableCellShouldBeSelectedPassesIfCellIsSelected() throws Throwable {
            checking(new Expectations() {{
                one(tableOperator).isCellSelected(row, columnIdentifier);
                will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.tableCellShouldBeSelected(row, columnIdentifier);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void tableCellShouldBeSelectedFailsIfCellIsNotSelected() throws Throwable {
            checking(new Expectations() {{
                one(tableOperator).isCellSelected(row, columnIdentifier);
                will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.tableCellShouldBeSelected(row, columnIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Cell '" + row + "', '" + columnIdentifier + "' is not selected."));
        }

        public void tableCellShouldNotBeSelectedPassesIfCellIsNotSelected() throws Throwable {
            checking(new Expectations() {{
                one(tableOperator).isCellSelected(row, columnIdentifier);
                will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.tableCellShouldNotBeSelected(row, columnIdentifier);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void tableCellShouldNotBeSelectedFailsIfCellIsSelected() throws Throwable {
            checking(new Expectations() {{
                one(tableOperator).isCellSelected(row, columnIdentifier);
                will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.tableCellShouldNotBeSelected(row, columnIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Cell '" + row + "', '" + columnIdentifier + "' is selected."));
        }
    }

    public class OperatingOnCellValues {
        private Object cellValue = new Object() {
            public String toString() {
                return "someValue";
            }
        };

        public TableKeywords create() {
            setMockTableOperatorAsContext();
            return createTableKeywordsWithMockContextVerifier();
        }

        public void getsTableCellValue() {
            checking(new Expectations() {{
                one(tableOperator).getValueAt(row, columnIdentifier);
                will(returnValue(cellValue));
            }});

            specify(context.getTableCellValue(row, columnIdentifier), must.equal(cellValue.toString()));
        }

        public void getsSelectedTableCellValue() {
            final int row = 5;
            final int column = 2;
            checking(new Expectations() {{
                one(tableOperator).getSelectedColumn(); will(returnValue(column));
                one(tableOperator).getSelectedRow(); will(returnValue(row));
                one(tableOperator).getValueAt(row, column);
                will(returnValue(cellValue));
            }});

            specify(context.getSelectedTableCellValue(), must.equal(cellValue.toString()));
        }
    }

    private TableKeywords createTableKeywordsWithMockContextVerifier() {
        TableKeywords tableKeywords = new TableKeywords();
        final IContextVerifier contextVerifier = injectMockTo(tableKeywords, "contextVerifier", IContextVerifier.class);

        checking(new Expectations() {{
            one(contextVerifier).verifyContext();
        }});

        return tableKeywords;
    }

    private void setMockTableOperatorAsContext() {
        tableOperator = mock(EnhancedTableOperator.class);
        Context.setContext(tableOperator);
    }
}
