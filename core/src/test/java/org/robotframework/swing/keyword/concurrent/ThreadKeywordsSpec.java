package org.robotframework.swing.keyword.concurrent;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;

@RunWith(JDaveRunner.class)
public class ThreadKeywordsSpec extends Specification<ThreadKeywords> {
    public class Any {
        public ThreadKeywords create() {
            return new ThreadKeywords();
        }
        
        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }
        
        public void hasRunKeywordInSeparateThreadKeyword() {
            specify(context, satisfies(new RobotKeywordContract("runKeywordInSeparateThread")));
        }
    }
}
