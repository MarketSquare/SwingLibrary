import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.netbeans.jemmy.JemmyProperties;
import org.robotframework.swing.keyword.timeout.TimeoutKeywords;
import org.robotframework.swing.security.SystemExitCatcher;

@RunWith(JDaveRunner.class)
public class SwingLibrarySpec extends Specification<SwingLibrary> {
    public class Any {
        public SwingLibrary create() {
            return new SwingLibrary();
        }

        public void doesntContainKeywordsThatDontMatchDefaultPathPattern() {
            specify(context.getKeywordNames(), must.not().contain("keywordThatShouldNotBeRegistered"));
        }

        public void printsNothingToStandardOut() {
            specify(JemmyProperties.getCurrentOutput().getOutput(), must.equal(null));
            specify(JemmyProperties.getCurrentOutput().getErrput(), must.equal(null));
        }

        public void setsTimeoutsTo5Seconds() {
            String[] timeouts = TimeoutKeywords.JEMMY_TIMEOUTS;
            for (String timeout : timeouts) {
                specify(JemmyProperties.getCurrentTimeout(timeout), must.equal(5000));
            }
        }
        
        public void catchesSystemExits() {
            specify(System.getSecurityManager().getClass(), must.equal(SystemExitCatcher.class));
        }
    }
}
