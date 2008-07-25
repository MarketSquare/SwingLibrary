package org.robotframework.swing.arguments;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.arguments.ArgumentHandler;
import org.robotframework.swing.arguments.ArgumentParser;


@RunWith(JDaveRunner.class)
public class ArgumentParserSpec extends Specification<ArgumentParser<String>> {
    public class WhenParsingArguments {
        private ArgumentHandler<String> handler;

        public ArgumentParser<String> create() {
            handler = mock(ArgumentHandler.class);
            ArgumentParser<String> argumentParser = new ArgumentParser<String>(handler);
            return argumentParser;
        }

        public void returnsIndexArgumentFromArgumentHandlerIfArgumentConvertibleToInt() {
            String argConvertibleToInt = "1";
            final String retValForIntArgument = "returnValueForIntArgument";

            checking(new Expectations() {{
                one(handler).indexArgument(1); will(returnValue(retValForIntArgument));
            }});

            specify(context.parseArgument(argConvertibleToInt), does.equal(retValForIntArgument));
        }

        public void returnsStringArgumentFromArgumentHandlerIfArgumentNotConvertibleToInt() {
            final String argNotConvertibleToInt = "notConvertibleToInt";
            final String retValForStringArgument = "returnValueForStringArgument";

            checking(new Expectations() {{
                one(handler).nameArgument(with(equal(argNotConvertibleToInt)));
                will(returnValue(retValForStringArgument));
            }});

            specify(context.parseArgument(argNotConvertibleToInt), does.equal(retValForStringArgument));
        }
    }
}
