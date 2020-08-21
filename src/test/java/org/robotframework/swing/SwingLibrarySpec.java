package org.robotframework.swing;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.netbeans.jemmy.JemmyProperties;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.keyword.timeout.TimeoutKeywords;

@RunWith(JDaveRunner.class)
public class SwingLibrarySpec extends MockSupportSpecification<SwingLibrary> {
    public class Any {
        public SwingLibrary create() {
            return new SwingLibrary();
        }

        public void doesntContainKeywordsThatDontMatchDefaultPathPattern() {
            specify(context.getKeywordNames(), must.not().contain("keywordThatShouldNotBeRegistered"));
        }

        public void outputsAreNull() {
            specify(JemmyProperties.getCurrentOutput().getOutput(), must.equal(null));
            specify(JemmyProperties.getCurrentOutput().getErrput(), must.equal(null));
        }

        public void setsTimeoutsTo5Seconds() {
            String[] timeouts = TimeoutKeywords.JEMMY_TIMEOUTS;
            for (String timeout : timeouts) {
                specify(JemmyProperties.getCurrentTimeout(timeout), must.equal(10000));
            }
        }
    }
}
