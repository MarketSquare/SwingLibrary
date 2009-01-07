import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.JemmyProperties;
import org.robotframework.javalib.library.AnnotationLibrary;
import org.robotframework.swing.keyword.MockSupportSpecification;
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
    }
    
    public class HandlingArguments {
        private AnnotationLibrary annotationLibrary;
        private String keywordName = "someKeyword";

        public SwingLibrary create () {
            SwingLibrary swingLibrary = new SwingLibrary();
            annotationLibrary = injectMockTo(swingLibrary, AnnotationLibrary.class);
            return swingLibrary;
        }

        public void convertsArgumentsToString() {
            Object[] arrayArgument = new Object[] {  };
            Object[] actualArguments = new Object[] { new Integer(2), Boolean.TRUE, arrayArgument };
            final Object[] expectedArguments = new Object[] { "2", "true", arrayArgument };
            
            checking(new Expectations() {{
                one(annotationLibrary).runKeyword(keywordName, expectedArguments);
                will(returnValue("something"));
            }});
            
            specify(context.runKeyword(keywordName, actualArguments), "something");
        }
    }
}
