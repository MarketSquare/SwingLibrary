package org.robotframework.swing.keyword.timeout;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.netbeans.jemmy.JemmyProperties;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.timeout.keyword.TimeoutKeywords;


@RunWith(JDaveRunner.class)
public class TimeoutKeywordsSpec extends Specification<TimeoutKeywords> {
    public class Any {
        public TimeoutKeywords create() {
            return new TimeoutKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasSetJemmyTimeoutKeyword() {
            specify(context, satisfies(new RobotKeywordContract("setJemmyTimeout")));
        }

        public void hasSetJemmyTimeoutsKeyword() {
            specify(context, satisfies(new RobotKeywordContract("setJemmyTimeouts")));
        }

        public void setsJemmyTimeout() {
            String timeoutName = "DialogWaiter.WaitDialogTimeout";
            context.setJemmyTimeout(timeoutName, "3");
            specify(JemmyProperties.getCurrentTimeout(timeoutName), must.equal(3000));
        }

        public void setsJemmyTimeouts() {
            context.setJemmyTimeouts("3");
            for (String timeoutName : TimeoutKeywords.JEMMY_TIMEOUTS) {
                specify(JemmyProperties.getCurrentTimeout(timeoutName), must.equal(3000));
            }
        }
    }
}
