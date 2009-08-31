package org.robotframework.swing.keyword.filechooser;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.filechooser.FileChooserOperatorFactory;

@RunWith(JDaveRunner.class)
public class FileChooserKeywordsSpec extends MockSupportSpecification<FileChooserKeywords> {
    public class Any {
        public FileChooserKeywords create() {
            return new FileChooserKeywords();
        }
        
        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }
        
        public void hasChooseFromFileChooserKeyword() {
            specify(context, satisfies(new RobotKeywordContract("chooseFromFileChooser")));
        }
        
        public void hasCancelFileChooserKeyword() {
            specify(context, satisfies(new RobotKeywordContract("cancelFileChooser")));
        }
        
        public void hasFileChooserOperatorFactory() {
            specify(context, satisfies(new FieldIsNotNullContract("fileChooserOperatorFactory")));
        }
    }
    
    public class ChoosingFiles {
        private String[] fileName = new String[] { "elements.xml" };
        private JFileChooserOperator fileChooserOperator;
        private FileChooserKeywords fileChooserKeywords = new FileChooserKeywords();
        
        public FileChooserKeywords create() {
            fileChooserOperator = mock(JFileChooserOperator.class);
            final FileChooserOperatorFactory factory = injectMockTo(fileChooserKeywords, FileChooserOperatorFactory.class);
            checking(new Expectations() {{
                one(factory).createFileChooserOperator(); will(returnValue(fileChooserOperator));
            }});
            return fileChooserKeywords;
        }
        
        public void choosesFile() {
            checking(new Expectations() {{
                one(fileChooserOperator).chooseFile("elements.xml");
            }});
            
            context.chooseFromFileChooser(fileName);
        }
        
        public void choosesDefaultFile() {
            checking(new Expectations() {{
                one(fileChooserOperator).approve();
            }});
            
            context.chooseFromFileChooser(new String[0]);
        }
        
        public void cancelsFileSelection() {
            checking(new Expectations() {{
                one(fileChooserOperator).cancelSelection();
            }});
            
            context.cancelFileChooser();
        }
    }
}
