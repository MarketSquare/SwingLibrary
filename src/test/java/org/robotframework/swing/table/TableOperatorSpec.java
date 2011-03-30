package org.robotframework.swing.table;

import java.awt.Component;
import java.awt.Point;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JPopupMenu;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsInstanceOf;
import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTableOperator.TableCellChooser;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.comparator.EqualsStringComparator;

@RunWith(JDaveRunner.class)
public class TableOperatorSpec extends MockSupportSpecification<TableOperator> {
    private String row = "2";
    private JTableOperator jTableOperator;
    private Object cellValue = new Object();
    private Point coordinates = new Point(2, 3);

    public class Any {
        public void requiresExactMatchWhenComparesValues() {
            jTableOperator = mock(JTableOperator.class);

            checking(new Expectations() {{
                one(jTableOperator).setComparator(with(any(EqualsStringComparator.class)));
            }});

            new TableOperator(jTableOperator);
        }
    }

    public class OperatingOnRowsAndColumn {
        public TableOperator create() {
            jTableOperator = mock(JTableOperator.class);
            checking(new Expectations() {{
                ignoring(jTableOperator).setComparator(with(any(EqualsStringComparator.class)));
            }});
            return new TableOperator(jTableOperator);
        }

        public void clearsSelection() {
            checking(new Expectations() {{
                one(jTableOperator).clearSelection();
            }});

            context.clearSelection();
        }

        public void findsCellRow() {
            checking(new Expectations() {{
                one(jTableOperator).findCellRow("someValue");
                will(returnValue(3));
            }});

            specify(context.findCellRow("someValue"), 3);
        }

        public void getsRowCount() {
            checking(new Expectations() {{
                one(jTableOperator).getRowCount();
                will(returnValue(3));
            }});

            specify(context.getRowCount(), 3);
        }

        public void getsColumnCount() {
            checking(new Expectations() {{
                one(jTableOperator).getColumnCount();
                will(returnValue(3));
            }});

            specify(context.getColumnCount(), 3);
        }

        public void getsSource() {
            final Component source = dummy(Component.class);
            checking(new Expectations() {{
                one(jTableOperator).getSource();
                will(returnValue(source));
            }});

            specify(context.getSource(), source);
        }

        public void selectingNonexistentCellFails() {
            checking(new Expectations() {{
                one(jTableOperator).findCell(with(any(TableCellChooser.class)));
                will(returnValue(new Point(-1, -1)));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.selectCell("12", "nonexisting");
                }
            }, should.raise(InvalidCellException.class));
        }


        public void getsTableHeaders() {
            final JTableHeader tableHeader = mock(JTableHeader.class);
            final TableColumnModel columnModel = mock(TableColumnModel.class);

            Vector<TableColumn> columnVector = new Vector<TableColumn>();
            for (int i = 0; i < 3; ++i) {
                final String header = "column" + i;
                final TableColumn col = mock(TableColumn.class, header);

                checking(new Expectations() {{
                    one(col).getHeaderValue();
                    will(returnValue(header));
                }});

                columnVector.add(col);
            }

            final Enumeration<TableColumn> columns = columnVector.elements();

            checking(new Expectations() {{
                one(jTableOperator).getTableHeader();
                will(returnValue(tableHeader));
                one(tableHeader).getColumnModel();
                will(returnValue(columnModel));
                one(columnModel).getColumns();
                will(returnValue(columns));
            }});

            specify(context.getTableHeaders(), containsInOrder("column0", "column1", "column2"));
        }
    }

    public class OperatingOnTableWithColumnIndex extends OperatingOnTable {
        protected JTableOperator createMockJTableOperator() {
            column = "2";
            jTableOperator = mock(JTableOperator.class);
            checking(new Expectations() {{
                one(jTableOperator).findCell(with(instanceOf(ColumnIndexTableCellChooser.class)));
                will(returnValue(coordinates));
                ignoring(jTableOperator).setComparator(with(any(EqualsStringComparator.class)));
            }});

            return jTableOperator;
        }
    }

    public class OperatingOnTableWithColumnHeaderName extends OperatingOnTable {
        protected JTableOperator createMockJTableOperator() {
            column = "someColumn";
            jTableOperator = mock(JTableOperator.class);
            checking(new Expectations() {{
                one(jTableOperator).findCell(with(instanceOf(ColumnNameTableCellChooser.class)));
                will(returnValue(coordinates));
                ignoring(jTableOperator).setComparator(with(any(EqualsStringComparator.class)));
            }});

            return jTableOperator;
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Matcher<T> instanceOf(Class<T> type) {
        return (Matcher<T>) new IsInstanceOf(type);
    }

    public abstract class OperatingOnTable {
        protected String column;

        protected abstract JTableOperator createMockJTableOperator();

        public TableOperator create() {
            return new TableOperator(createMockJTableOperator());
        }

        public void checksCellIsSelected() {
            checking(new Expectations() {{
                one(jTableOperator).isCellSelected(coordinates.y, coordinates.x);
                will(returnValue(true));
            }});

            specify(context.isCellSelected(row, column));
        }

        public void selectsCell() {
            checking(new Expectations() {{
                one(jTableOperator).selectCell(coordinates.y, coordinates.x);
            }});

            context.selectCell(row, column);
        }

        public void setsCellValue() {
            final Object newValue = new Object();
            checking(new Expectations() {{
                one(jTableOperator).setValueAt(newValue, coordinates.y, coordinates.x);
            }});

            context.setCellValue(newValue, row, column);
        }

        public void typesIntoCell() {
            final Object newValue = new Object();
            checking(new Expectations() {{
                one(jTableOperator).changeCellObject(coordinates.y, coordinates.x, newValue);
            }});

            context.typeIntoCell(newValue, row, column);
        }

        public void clearsCell() {
            checking(new Expectations() {{
                one(jTableOperator).changeCellObject(coordinates.y, coordinates.x, "");
            }});

            context.clearCell(row, column);
        }

        public void callsPopupOnCell() {
            final JPopupMenu popupMenu = dummy(JPopupMenu.class);
            checking(new Expectations() {{
                one(jTableOperator).callPopupOnCell(coordinates.y, coordinates.x);
                will(returnValue(popupMenu));
            }});

            specify(context.callPopupOnCell(row, column).getSource(), popupMenu);
        }
    }

}
