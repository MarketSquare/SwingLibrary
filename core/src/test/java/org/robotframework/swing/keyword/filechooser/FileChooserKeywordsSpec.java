package org.robotframework.swing.keyword.filechooser;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.swing.filechooser.FileChooserOperator;
import org.robotframework.swing.filechooser.FileChooserOperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;

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
        private String fileName = "elements.xml";
        private FileChooserOperator fileChooserOperator;
        private FileChooserKeywords fileChooserKeywords = new FileChooserKeywords();
        
        public FileChooserKeywords create() {
            fileChooserOperator = mock(FileChooserOperator.class);
            final FileChooserOperatorFactory factory = injectMockTo(fileChooserKeywords, FileChooserOperatorFactory.class);
            checking(new Expectations() {{
                one(factory).createFileChooserOperator(); will(returnValue(fileChooserOperator));
            }});
            return fileChooserKeywords;
        }
        
        public void choosesFile() {
            checking(new Expectations() {{
                one(fileChooserOperator).chooseFile(fileName);
            }});
            
            context.chooseFromFileChooser(fileName);
        }
        
        public void cancelsFileSelection() {
            checking(new Expectations() {{
                one(fileChooserOperator).cancelSelection();
            }});
            
            context.cancelFileChooser();
        }
    }
}
