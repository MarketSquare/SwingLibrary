package org.robotframework.swing.keyword.table;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.swing.comparator.EqualsStringComparator;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.table.DefaultTableOperator;
import org.robotframework.swing.table.TableOperator;


@RunWith(JDaveRunner.class)
public class TableKeywordsSpec extends MockSupportSpecification<TableKeywords> {
    private TableKeywords tableKeywords = new TableKeywords();
    private OperatorFactory<?> operatorFactory;
    private TableOperator tableOperator;
    private String tableIdentifier = "someTable";
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

        public void hasClearTableCellValueKeyword() {
            specify(context, satisfies(new RobotKeywordContract("clearTableCell")));
        }
        
        public void hasSelectFromTableCellPopupMenuKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectFromTableCellPopupMenu")));
        }
        
        public void hasOperatorFactory() {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }
    }

    public class GettingCellValuesFromTable {
        private Object cellValue = new Object() {
            public String toString() {
                return "someValue";
            }
        };
        
        public TableKeywords create() {
            injectMockOperatorFactory();
            return tableKeywords;
        }
        
        public void getsSelectedTableCellValue() {
            checking(new Expectations() {{
                one(tableOperator).getSelectedCellValue();
                will(returnValue(cellValue));
            }});

            specify(context.getSelectedTableCellValue(tableIdentifier), must.equal(cellValue.toString()));
        }
        
        public void getsTableCellValue() {
            checking(new Expectations() {{
                one(tableOperator).getCellValue(row, columnIdentifier);
                will(returnValue(cellValue));
            }});

            specify(context.getTableCellValue(tableIdentifier, row, columnIdentifier), must.equal(cellValue.toString()));
        }
    }
    
    public class OperatingOnTable {
        public TableKeywords create() {
            injectMockOperatorFactory();
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
                one(tableOperator).setCellValue(newValue, row, columnIdentifier);
            }});

            context.setTableCellValue(tableIdentifier, row, columnIdentifier, newValue);
        }

        public void selectsTableCell() {
            checking(new Expectations() {{
                one(tableOperator).selectCell(row, columnIdentifier);
            }});

            context.selectTableCell(tableIdentifier, row, columnIdentifier);
        }

        public void clearsTableSelection() {
            checking(new Expectations() {{
                one(tableOperator).clearSelection();
            }});

            context.clearTableSelection(tableIdentifier);
        }

        public void tableCellShouldBeSelectedPassesIfCellIsSelected() throws Throwable {
            checking(new Expectations() {{
                one(tableOperator).isCellSelected(row, columnIdentifier);
                will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.tableCellShouldBeSelected(tableIdentifier, row, columnIdentifier);
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
                    context.tableCellShouldBeSelected(tableIdentifier, row, columnIdentifier);
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
                    context.tableCellShouldNotBeSelected(tableIdentifier, row, columnIdentifier);
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
                    context.tableCellShouldNotBeSelected(tableIdentifier, row, columnIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Cell '" + row + "', '" + columnIdentifier + "' is selected."));
        }

        public void clearsTableCell() {
            checking(new Expectations() {{
                one(tableOperator).clearCell(row, columnIdentifier);
            }});
            
            context.clearTableCell(tableIdentifier, row, columnIdentifier);
        }
    }
    
    public class CallingPopupOnTableCell {
        private String menuPath = "some|menu";
        
        public TableKeywords create() {
            injectMockOperatorFactory();
            return tableKeywords;
        }
        
        public void selectsFromTableCellPopupMenu() {
            final JPopupMenuOperator popupMenuOperator = mock(JPopupMenuOperator.class);
            final JMenuItemOperator menuItemOperator = mock(JMenuItemOperator.class);
            
            checking(new Expectations() {{
                one(tableOperator).callPopupOnCell(row, columnIdentifier);
                will(returnValue(popupMenuOperator));
                
                one(popupMenuOperator).showMenuItem(with(equal(menuPath)), with(any(EqualsStringComparator.class)));
                will(returnValue(menuItemOperator));
                
                one(menuItemOperator).push();
            }});
            
            context.selectFromTableCellPopupMenu(tableIdentifier, row, columnIdentifier, menuPath);
        }
    }

    private void injectMockOperatorFactory() {
        operatorFactory = injectMockTo(tableKeywords, OperatorFactory.class);
        tableOperator = mock(DefaultTableOperator.class);

        checking(new Expectations() {{
            one(operatorFactory).createOperator(tableIdentifier);
            will(returnValue(tableOperator));
        }});
    }
}
