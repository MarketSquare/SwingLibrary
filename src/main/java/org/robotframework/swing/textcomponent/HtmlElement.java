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

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;

public class HtmlElement implements Element {
    private final Element element;

    public HtmlElement(Element element) {
        this.element = element;
    }

    public AttributeSet getAttributes() {
        return element.getAttributes();
    }

    public Document getDocument() {
        return element.getDocument();
    }

    public Element getElement(int index) {
        return element.getElement(index);
    }

    public int getElementCount() {
        return element.getElementCount();
    }

    public int getElementIndex(int offset) {
        return element.getElementIndex(offset);
    }

    public int getEndOffset() {
        return element.getEndOffset();
    }

    public String getName() {
        return element.getName();
    }

    public Element getParentElement() {
        return element.getParentElement();
    }

    public int getStartOffset() {
        return element.getStartOffset();
    }

    public boolean isLeaf() {
        return element.isLeaf();
    }

    public String getText() {
        int start = getStartOffset();
        int length = getEndOffset() - start;
        try {
            return getDocument().getText(start, length);
        } catch (BadLocationException e1) {
            throw new RuntimeException(e1);
        }
    }

    public HtmlElement getChild(int index) {
        return new HtmlElement(getElement(index));
    }
    
    public URL getUrl() {
        String href = getHref();
        URL base = getBase();
        return createURL(base, href);
    }
    
    public String getHref() {
        return (String)getA().getAttribute(HTML.Attribute.HREF);
    }
    
    private URL getBase() {
        return ((HTMLDocument) getDocument()).getBase();
    }

    private AttributeSet getA() {
        return (AttributeSet) getAttributes().getAttribute(HTML.Tag.A);
    }
    
    URL createURL(URL base, String href) {
        try {
            return new URL(base, href);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
