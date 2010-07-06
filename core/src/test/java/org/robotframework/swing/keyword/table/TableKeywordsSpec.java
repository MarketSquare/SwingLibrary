package org.robotframework.swing.keyword.table;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.comparator.EqualsStringComparator;
import org.robotframework.swing.factory.OperatorFactory;
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

        public void hasSelectTableCellAreaKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectTableCellArea")));
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
        
        public void hasTableCellPopupMenuShouldBeEnabledKeyword() {
            specify(context, satisfies(new RobotKeywordContract("tableCellPopupMenuShouldBeEnabled")));
        }
        
        public void hasTableCellPopupMenuShouldBeDisabledKeyword() {
            specify(context, satisfies(new RobotKeywordContract("tableCellPopupMenuShouldBeDisabled")));
        }
        
        public void hasTypeIntoTableCellKeyword() {
            specify(context, satisfies(new RobotKeywordContract("typeIntoTableCell")));
        }
        
        public void hasFindTableRowKeyword() {
            specify(context, satisfies(new RobotKeywordContract("findTableRow")));
        }
        
        public void hasGetTableHeadersKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getTableHeaders")));
        }
        
        public void hasGetTableColumnValuesKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getTableColumnValues")));
        }
        
        public void hasGetTableCellPropertiesKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getTableCellProperty")));
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
        
		public void getsTableCellProperties() {
			@SuppressWarnings("serial")
            final Map<String, Object> props = new HashMap<String, Object>() {{
                put("background", Color.black);
            }};
            
            checking(new Expectations() {{
                one(tableOperator).getCellProperties(row, columnIdentifier);
                will(returnValue(props)); 
            }});
            
            Object cellProperty = context.getTableCellProperty(tableIdentifier, row, columnIdentifier, "background");
            specify(cellProperty, Color.black);
        }
    }
    
    public class OperatingOnTable {
        public TableKeywords create() {
            injectMockOperatorFactory();
            return tableKeywords;
        }
        
        public void getsTableHeaders() {
            final String[] tableHeaders = new String[] {"1", "2", "3"};
            checking(new Expectations() {{
                one(tableOperator).getTableHeaders(); will(returnValue(tableHeaders));
            }});
            
            specify(context.getTableHeaders(tableIdentifier), containsInOrder("1", "2", "3"));
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
        
        public void findsRow() {
            checking(new Expectations() {{
                one(tableOperator).findCellRow("someValue"); will(returnValue(7));
            }});

            specify(context.findTableRow(tableIdentifier, "someValue", null), must.equal(7));
        }

        public void setsTableCellValue() {
            final String newValue = "newValue";

            checking(new Expectations() {{
                one(tableOperator).setCellValue(newValue, row, columnIdentifier);
            }});

            context.setTableCellValue(tableIdentifier, row, columnIdentifier, newValue);
        }

        public void typesIntoTableCell() {
            final String newValue = "newValue";

            checking(new Expectations() {{
                one(tableOperator).typeIntoCell(newValue, row, columnIdentifier);
            }});

            context.typeIntoTableCell(tableIdentifier, row, columnIdentifier, newValue);
        }

        public void clicksOnTableCellNoOptArgs() {
        	checking(new Expectations() {{
                one(tableOperator).clickOnCell(row, "0", "1", "BUTTON1_MASK", new String[0]);
            }});
        	context.clickOnTableCell(tableIdentifier, row, "0", new String[0]);
        }
        
        public void clicksOnTableCellWithKeyAliases() {
        	checking(new Expectations() {{
                one(tableOperator).clickOnCell(row, "column_identifier", "7", "CTRL_MASK",
                		                       new String[] {"SHIFT_MASK", "META_MASK", "WHATEVER_MASK"});
            }});
        	context.clickOnTableCell(tableIdentifier, row, "column_identifier",
        			                 new String[] {"7", "ctrl", "SHIFT", "MeTa", "WHATEVER_MASK"});
        }

        public void doubleClicksOnTableCell() {
        	checking(new Expectations() {{
                one(tableOperator).clickOnCell(row, "0", "2", "BUTTON1_MASK", new String[0]);
            }});
        	context.clickOnTableCell(tableIdentifier, row, "0", new String[] {"2"});
        }
        
        public void doubleClicksOnTableCellWithSeveralButtons() {
        	checking(new Expectations() {{
                one(tableOperator).clickOnCell(row, "column_identifier", "2", "BUTTON2_MASK",
                		                       new String[] {"CTRL_MASK", "SHIFT_MASK", "META_MASK"});
            }});
        	context.clickOnTableCell(tableIdentifier, row, "column_identifier",
        			                 new String[] {"2", "RIGHT BUTTON", "CTRL_MASK", "SHIFT", "META_MASK"});
        }
        
        public void selectsTableCell() {
            checking(new Expectations() {{
                one(tableOperator).selectCell(row, columnIdentifier);
            }});

            context.selectTableCell(tableIdentifier, row, columnIdentifier);
        }
        
        public void selectsTableCellArea() {
            final String columnStart = "0";
            final String columnEnd = "1";
            final String rowStart = "0";
            final String rowEnd = "2";
            checking(new Expectations() {{
                one(tableOperator).selectCellArea(columnStart, columnEnd, rowStart, rowEnd);
            }});
            
            context.selectTableCellArea(tableIdentifier, columnStart, columnEnd, rowStart, rowEnd);
        }
        
        public void getsTableColumnValues() {
            checking(new Expectations() {{
                one(tableOperator).getColumnValues(columnIdentifier);
                will(returnValue(new Object[] { "one", "two", "three" }));
            }});
            
            specify(context.getTableColumnValues(tableIdentifier, columnIdentifier), containsExactly("one", "two", "three"));
        }

        public void clearsTableSelection() {
            checking(new Expectations() {{
                one(tableOperator).clearSelection();
            }});

            context.clearTableSelection(tableIdentifier);
        }

        public void tableCellShouldBeEditablePassesWhenItIs() throws Throwable {
            checking(new Expectations() {{
                one(tableOperator).isCellEditable(row, columnIdentifier);
                will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.tableCellShouldBeEditable(tableIdentifier, row, columnIdentifier);
                }
            }, must.not().raise(AssertionFailedError.class));
        }
        
        public void tableCellShouldBeEditableFailsWhenItIsNot() throws Throwable {
            checking(new Expectations() {{
                one(tableOperator).isCellEditable(row, columnIdentifier);
                will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.tableCellShouldBeEditable(tableIdentifier, row, columnIdentifier);
                }
            }, must.raise(AssertionFailedError.class));
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
        private JPopupMenuOperator popupMenuOperator;
        private JMenuItemOperator menuItemOperator;
        
        public TableKeywords create() {
            injectMockOperatorFactory();
            
            popupMenuOperator = mock(JPopupMenuOperator.class);
            menuItemOperator = mock(JMenuItemOperator.class);
            
            checking(new Expectations() {{
                one(tableOperator).callPopupOnCell(row, columnIdentifier);
                will(returnValue(popupMenuOperator));
                
                one(popupMenuOperator).showMenuItem(with(equal(menuPath)), with(any(EqualsStringComparator.class)));
                
                will(returnValue(menuItemOperator));
            }});
            
            return tableKeywords;
        }
        
        public void selectsFromTableCellPopupMenu() {
            checking(new Expectations() {{    
                one(menuItemOperator).push();
            }});
            
            context.selectFromTableCellPopupMenu(tableIdentifier, row, columnIdentifier, menuPath);
        }
        
        public void tableCellPopupMenuShouldBeEnabledPassesWhenMenuItemIsEnabled() throws Throwable {
            checking(new Expectations() {{    
                one(menuItemOperator).isEnabled(); will(returnValue(true));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.tableCellPopupMenuShouldBeEnabled(tableIdentifier, row, columnIdentifier, menuPath);
                }
            }, must.not().raiseAnyException());
        }
        
        public void tableCellPopupMenuShouldBeEnabledFailsWhenMenuItemIsDisabled() throws Throwable {
            checking(new Expectations() {{    
                one(menuItemOperator).isEnabled(); will(returnValue(false));
            }});
            
            String expectedMessage = "Menuitem '" + menuPath + "' at '" + row + ", " +  columnIdentifier + "' is disabled.";
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.tableCellPopupMenuShouldBeEnabled(tableIdentifier, row, columnIdentifier, menuPath);
                }
            }, must.raiseExactly(AssertionFailedError.class, expectedMessage));
        }
        
        public void tableCellPopupMenuShouldBeDisabledPassesWhenMenuItemIsDisabled() throws Throwable {
            checking(new Expectations() {{    
                one(menuItemOperator).isEnabled(); will(returnValue(false));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.tableCellPopupMenuShouldBeDisabled(tableIdentifier, row, columnIdentifier, menuPath);
                }
            }, must.not().raiseAnyException());
        }
        
        public void tableCellPopupMenuShouldBeDisabledFailsWhenMenuItemIsEnabled() throws Throwable {
            checking(new Expectations() {{    
                one(menuItemOperator).isEnabled(); will(returnValue(true));
            }});
            
            String expectedMessage = "Menuitem '" + menuPath + "' at '" + row + ", " +  columnIdentifier + "' is enabled.";
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.tableCellPopupMenuShouldBeDisabled(tableIdentifier, row, columnIdentifier, menuPath);
                }
            }, must.raiseExactly(AssertionFailedError.class, expectedMessage));
        }
    }

    private void injectMockOperatorFactory() {
        operatorFactory = injectMockTo(tableKeywords, OperatorFactory.class);
        tableOperator = mock(TableOperator.class);

        checking(new Expectations() {{
            one(operatorFactory).createOperator(tableIdentifier);
            will(returnValue(tableOperator));
        }});
    }
}
