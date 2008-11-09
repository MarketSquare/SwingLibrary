package org.robotframework.swing.table;

import java.awt.Component;
import java.awt.Point;

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsInstanceOf;
import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTableOperator.TableCellChooser;

@RunWith(JDaveRunner.class)
public class DefaultTableOperatorSpec extends Specification<DefaultTableOperator> {
    private String row = "2";
    private JTableOperator jTableOperator;
    private Object cellValue = new Object();
    private Point coordinates = new Point(2, 3);
    
    public class OperatingOnCellValues {
        public DefaultTableOperator create() {
            jTableOperator = mock(JTableOperator.class);
            return new DefaultTableOperator(jTableOperator);
        }
        
        public void clearsSelection() {
            checking(new Expectations() {{
                one(jTableOperator).clearSelection(); 
            }});
            
            context.clearSelection();
        }
        
        public void getsRowCount() {
            checking(new Expectations() {{
                one(jTableOperator).getRowCount(); will(returnValue(3)); 
            }});
            
            specify(context.getRowCount(), 3);
        }
        
        public void getsColumnCount() {
            checking(new Expectations() {{
                one(jTableOperator).getColumnCount(); will(returnValue(3)); 
            }});
            
            specify(context.getColumnCount(), 3);
        }
        
        public void getsSource() {
            final Component source = dummy(Component.class);
            checking(new Expectations() {{
                one(jTableOperator).getSource(); will(returnValue(source)); 
            }});
            
            specify(context.getSource(), source);
        }
        
        public void selectingNonexistentCellFails() {
        	checking(new Expectations() {{
        		one(jTableOperator).findCell(with(any(TableCellChooser.class))); will(returnValue(new Point(-1, -1)));
        	}});
        	
            specify(new Block() {
                public void run() throws Throwable {
                    context.selectCell("12", "nonexisting");
                }
            }, should.raise(InvalidCellException.class));
        }
    }
    
    public class RetrievingCellValues {
        private Point coordinates = new Point(2, 3);
        
        public DefaultTableOperator create() {
            jTableOperator = mock(JTableOperator.class);
            return new DefaultTableOperator(jTableOperator);
        }

        public void getsSelectedCellValue() {
            checking(new Expectations() {{
                one(jTableOperator).getSelectedRow(); will(returnValue(2));
                one(jTableOperator).getSelectedColumn(); will(returnValue(3));
                one(jTableOperator).getValueAt(2, 3); will(returnValue(cellValue));
            }});
            
            specify(context.getSelectedCellValue(), cellValue);
        }
    }
    
    public class OperatingOnTableWithColumnIndex {
        private String columnIndexAsString = "2";
        
        public DefaultTableOperator create() {
            jTableOperator = mock(JTableOperator.class);
            checking(new Expectations() {{
                one(jTableOperator).findCell(with(instanceOf(ColumnIndexTableCellChooser.class))); will(returnValue(coordinates));
            }});
            
            return new DefaultTableOperator(jTableOperator);
        }
        
        public void getsCellValue() {
            checking(new Expectations() {{
                one(jTableOperator).getValueAt(coordinates.y, coordinates.x); will(returnValue(cellValue));
            }});
            
            specify(context.getCellValue(row, columnIndexAsString), cellValue);
        }
        
        public void checksCellIsSelected() {
            checking(new Expectations() {{
                one(jTableOperator).isCellSelected(coordinates.y, coordinates.x); will(returnValue(true));
            }});
            
            specify(context.isCellSelected(row, columnIndexAsString));
        }
        
        public void selectsCell() {
            checking(new Expectations() {{
                one(jTableOperator).selectCell(coordinates.y, coordinates.x);
            }});
            
            context.selectCell(row, columnIndexAsString);
        }
        
        public void setsCellValue() {
            final Object newValue = new Object();
            checking(new Expectations() {{
                one(jTableOperator).changeCellObject(coordinates.y, coordinates.x, newValue);
            }});
            
            context.setCellValue(newValue, row, columnIndexAsString);
        }
    }
    
    public class OperatingOnTableWithColumnHeaderName {
        private String columnHeaderName = "someColumn";
        
        public DefaultTableOperator create() {
            jTableOperator = mock(JTableOperator.class);
            checking(new Expectations() {{
                one(jTableOperator).findCell(with(instanceOf(ColumnNameTableCellChooser.class))); will(returnValue(coordinates));
            }});
            
            return new DefaultTableOperator(jTableOperator);
        }
        
        public void getsCellValue() {
            checking(new Expectations() {{
                one(jTableOperator).getValueAt(coordinates.y, coordinates.x); will(returnValue(cellValue));
            }});
            
            specify(context.getCellValue(row, columnHeaderName), cellValue);
        }
        
        public void checksCellIsSelected() {
            checking(new Expectations() {{
                one(jTableOperator).isCellSelected(coordinates.y, coordinates.x); will(returnValue(true));
            }});
            
            specify(context.isCellSelected(row, columnHeaderName));
        }
        
        public void selectsCell() {
            checking(new Expectations() {{
                one(jTableOperator).selectCell(coordinates.y, coordinates.x);
            }});
            
            context.selectCell(row, columnHeaderName);
        }
        
        public void setsCellValue() {
            final Object newValue = new Object();
            checking(new Expectations() {{
                one(jTableOperator).changeCellObject(coordinates.y, coordinates.x, newValue);
            }});
            
            context.setCellValue(newValue, row, columnHeaderName);
        }
        
        public void clearsCell() {
            checking(new Expectations() {{
                one(jTableOperator).prepareEditor(with(instanceOf(CellClearingEditor.class)), with(equal(coordinates.y)), with(equal(coordinates.x)));
            }});
            
            context.clearCell(row, columnHeaderName);
        }
    }
    
    @SuppressWarnings("unchecked")
    private <T> Matcher<T> instanceOf(Class<T> type) {
        return (Matcher<T>) new IsInstanceOf(type);
    }
}
