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

package org.robotframework.swing.textcomponent;

import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;

import org.netbeans.jemmy.operators.JEditorPaneOperator;

public class HyperlinkEventFactory {
    private final JEditorPane editorPane;
    private final HTMLDocument document;

    public HyperlinkEventFactory(JEditorPaneOperator editorPaneOperator) {
        this.editorPane = (JEditorPane) editorPaneOperator.getSource();
        this.document = (HTMLDocument) editorPaneOperator.getDocument();
    }

    public HyperlinkEvent createHyperLinkEvent(String linkText) {
        HtmlElement linkElement = findElement(linkText);
        URL url = linkElement.getUrl();
        String description = linkElement.getHref();
        return new HyperlinkEvent(editorPane, HyperlinkEvent.EventType.ACTIVATED, url, description, linkElement);
    }

    private HtmlElement findElement(String linkText) {
        Element[] rootElements = document.getRootElements();
        for (Element element : rootElements) {
            try {
                findElement(new HtmlElement(element), linkText);
            } catch (ElementFound finding) {
                return finding.element;
            }
        }
        throw new LinkNotFoundException("Hyperlink '" + linkText + "' was not found");
    }
    
    private void findElement(HtmlElement element, String linkText) throws ElementFound {
        for (int i = 0; i < element.getElementCount(); ++i) {
            findElement(element.getChild(i), linkText);
        }
        
        if (linkText.equals(element.getText())) {
            throw new ElementFound(element);
        }
    }
    
    private static class ElementFound extends Exception {
        final HtmlElement element;

        public ElementFound(Element element) {
            this.element = new HtmlElement(element);
        }
    }
}
