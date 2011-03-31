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


package org.robotframework.swing.chooser;

import java.awt.Component;

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class ByTitleComponentChooserSpec extends Specification<ByTitleComponentChooser> {
    public class Any {
        private String title = "someTitle";
        
        public ByTitleComponentChooser create() {
            return new ByTitleComponentChooser("someTitle");
        }
        
        public void failsIfComponentHasNoGetterForTitle() throws Throwable {
            specify(new Block() {
                public void run() throws Throwable {
                    context.checkComponent(dummy(Component.class));
                }
            }, raise(Exception.class));
        }
        
        public void choosesAnyComponentWithTitle() {
            specify(context.checkComponent(new ComponentWithTitle(title)));
        }
        
        public void describedWithExpectedTitle() {
            specify(context.getDescription(), must.equal(title));
        }
    }
    
    private static class ComponentWithTitle extends Component {
        private final String title;

        public ComponentWithTitle(String title) {
            this.title = title;
        }
        
        public String getTitle() {
            return title;
        }
    }
}
