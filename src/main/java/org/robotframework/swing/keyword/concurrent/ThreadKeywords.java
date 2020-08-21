/*
 * Copyright 2008-2011 Nokia Siemens Networks Oyj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.robotframework.swing.keyword.concurrent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.javalib.util.KeywordNameNormalizer;
import org.robotframework.swing.SwingLibrary;

@RobotKeywords
public class ThreadKeywords {
    private KeywordNameNormalizer normalizer = new KeywordNameNormalizer();

    @RobotKeyword("Executes the given keyword with the given arguments in a separate thread.\n"
        + "Useful if the given keyword starts a process that stays running (e.g. opens a dialog) and doesn't return.\n"
        + "Known limitations:\n"
        + "- The return value of the keyword being run is ignored.\n"
        + "- Exceptions thrown by the keyword are ignored, which implies that this keyword should not be used\n"
        + "  in conjunction with keywords such `Label Text Should Be` or `Menu Item Should Be Enabled`.\n")
    @ArgumentNames({"keywordName", "*arguments"})
    public void runKeywordInSeparateThread(final String keywordName, final Object... arguments) {
        assertKeywordExists(keywordName);

        assertArgumentCountIsCorrect(keywordName, arguments);
        new Thread() {
            public void run() {
                SwingLibrary.instance.runKeyword(keywordName, Arrays.asList(arguments));
            }
        }.start();
    }

    public String stringify(String keywordName, Object... arguments) {
        assertKeywordExists(keywordName);
        assertArgumentCountIsCorrect(keywordName, arguments);
        return SwingLibrary.instance.runKeyword(keywordName, Arrays.asList(arguments)).toString();
    }

    private void assertArgumentCountIsCorrect(String keywordName, Object[] arguments) {
        List<String> keywordArguments = SwingLibrary.instance.getKeywordArguments(normalizer.normalize(keywordName));
        if (keywordArguments == null)
            return;
        if (hasVarArgs(keywordArguments)) {
            String errorMessage = "Expected " + (keywordArguments.size()-1) + " or more but got " + arguments.length + " arguments.     ";
            Assert.assertTrue(errorMessage, keywordArguments.size()-1 <= arguments.length);
        } else {
            String errorMessage = "Expected " + keywordArguments.size() + " but got " + arguments.length + " arguments.     ";
            Assert.assertEquals(errorMessage, keywordArguments.size(), arguments.length);
        }
    }

    private boolean hasVarArgs(List<String> keywordArguments) {
        return keywordArguments.size() > 0 && keywordArguments.get(keywordArguments.size()-1).startsWith("*");
    }

    private void assertKeywordExists(final String keywordName) {
        Collection<String> normalizedKeywordNames = normalizeKeywordNames(SwingLibrary.instance.getKeywordNames());
        String normalizedKeywordName = normalizer.normalize(keywordName);
        boolean keywordExists = normalizedKeywordNames.contains(normalizedKeywordName);
        Assert.assertTrue("Keyword '" + keywordName + "' was not found.", keywordExists);
    }

    @SuppressWarnings("unchecked")
    private Collection<String> normalizeKeywordNames(List<String> keywordNames) {
        return CollectionUtils.collect(keywordNames, new Transformer() {
            public Object transform(Object input) {
                return normalizer.normalize(input.toString());
            }
        });
    }
}
