package org.robotframework.swing.keyword.keyboard;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;

@RunWith(JDaveRunner.class)
public class KeyEventKeywordsSpec extends Specification<KeyEventKeywords> {
    public class Any {
        public KeyEventKeywords create () {
            return new KeyEventKeywords();
        }
        
        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }
        
        public void hasSendKeyEventKeyword() {
            specify(context, satisfies(new RobotKeywordContract("sendKeyEvent")));
        }
    }
}
