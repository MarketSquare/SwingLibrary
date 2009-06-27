/*
 * Copyright 2008 Nokia Siemens Networks Oyj
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

package org.robotframework.swing.keyword.textcomponent;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

import junit.framework.Assert;

import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JEditorPaneOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.chooser.ByNameComponentChooser;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.textcomponent.TextComponentOperator;
import org.robotframework.swing.textcomponent.TextComponentOperatorFactory;

@RobotKeywords
public class TextComponentKeywords {
    private OperatorFactory<TextComponentOperator> operatorFactory = new TextComponentOperatorFactory();

    @RobotKeyword("Inserts text into a text component.\n\n"
        + "Example:\n"
        + "| Insert Into Textfield | _nameTextField_ | _John Doe_ |\n")
    public void insertIntoTextField(String identifier, String text) {
        createOperator(identifier).setText(text);
    }
    
    @RobotKeyword("Returns the value of a text component.\n\n"
        + "Example:\n"
        + "| ${textFieldValue}= | Get Textfield Value | _nameTextField_       |\n"
        + "| Should Be Equal    | _John Doe_          | _${textFieldValue}_ |\n")
    public String getTextFieldValue(String identifier) {
        return createOperator(identifier).getText();
    }

    @RobotKeyword("Types text into a text component.\n"
        + "Same as `Insert Into Textfield` but sends real key events when setting text field contents.\n"
        + "Useful if application expects real keyboard events instead of only setting the text of the textfield.\n\n"
        + "Example:\n"
        + "| Type Into Textfield | _nameTextField_ | _John Doe_ |\n")
    public void typeIntoTextField(String identifier, String text) {
        createOperator(identifier).typeText(text);
    }

    @RobotKeyword("Clears text field contents.\n\n"
        + "Example:\n"
        + "| Clear Textfield | _nameTextField_ |\n")
    public void clearTextField(String identifier) {
        createOperator(identifier).clearText();
    }

    @RobotKeyword("Fails if text component is disabled.\n\n"
        + "Example:\n"
        + "| Textfield Should Be Enabled | _nameTextField_ |\n")
    public void textFieldShouldBeEnabled(String identifier) {
        Assert.assertTrue("Textfield '" + identifier + "' is disabled.", createOperator(identifier).isEnabled());
    }
    
    @RobotKeyword("Fails if text component is enabled.\n\n"
        + "Example:\n"
        + "| Textfield Should Be Disabled | _nameTextField_ |\n")
    public void textFieldShouldBeDisabled(String identifier) {
        Assert.assertFalse("Textfield '" + identifier + "' is enabled.", createOperator(identifier).isEnabled());
    }

    @RobotKeyword
    public void clickHyperLink(String identifier, String link) {
        JEditorPaneOperator operator = new JEditorPaneOperator((ContainerOperator) Context.getContext(), new ByNameComponentChooser(identifier));
        HTMLDocument document = (HTMLDocument) operator.getDocument();
        Element linkElement = findElement(document, link);
        
        JEditorPane html = (JEditorPane) operator.getSource();
        URL base = document.getBase();
        
        String href = (String)((AttributeSet) linkElement.getAttributes().getAttribute(HTML.Tag.A)).getAttribute(HTML.Attribute.HREF);
        URL url = null;
        try {
            url = new URL(base, href);
        } catch (MalformedURLException e) {
            //IGNORE
        }
        HTMLFrameHyperlinkEvent event = new HTMLFrameHyperlinkEvent(html, HyperlinkEvent.EventType.ACTIVATED, url, linkElement, "_self");
        operator.fireHyperlinkUpdate(event);
    }
    
    private Element findElement(Document document, String linkText) {
        Element[] rootElements = document.getRootElements();
        for (Element element : rootElements) {
            try {
                findElement(element, linkText);
            } catch (ElementFound e) {
                return e.element;
            }
        }
        throw new RuntimeException("Couldn't find the specified link");
    }
    
    private void findElement(Element element, String linkText) throws ElementFound {
        for (int i = 0; i < element.getElementCount(); ++i) {
            findElement(element.getElement(i), linkText);
        }
        
        String text = getText(element);
        if (linkText.equals(text)) {
            throw new ElementFound(element);
        }
    }

    private String getText(Element element) {
        int start = element.getStartOffset();
        int length = element.getEndOffset() - start;
        try {
            return element.getDocument().getText(start, length);
        } catch (BadLocationException e1) {
            throw new RuntimeException(e1);
        }
    }
    
    private TextComponentOperator createOperator(String identifier) {
        return operatorFactory.createOperator(identifier);
    }
    
    private class ElementFound extends Exception {
        final Element element;

        public ElementFound(Element element) {
            this.element = element;
        }
    }
}
