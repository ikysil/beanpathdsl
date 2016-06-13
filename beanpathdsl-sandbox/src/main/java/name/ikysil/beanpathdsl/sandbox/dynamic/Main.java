/*
 * Copyright 2016 Illya Kysil <ikysil@ikysil.name>.
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
package name.ikysil.beanpathdsl.sandbox.dynamic;

import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.xml.datatype.XMLGregorianCalendar;

import static name.ikysil.beanpathdsl.dynamic.DynamicBeanPath.expr;
import static name.ikysil.beanpathdsl.dynamic.DynamicBeanPath.path;
import static name.ikysil.beanpathdsl.dynamic.DynamicBeanPath.root;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(path(root(TestBeanA.class).getStrings().isEmpty()));
        System.out.println(path(root(TestBeanA.class).getArray()));
        System.out.println(path(root(TestBeanA.class).get2DArray()));
        System.out.println(path(root(TestBeanA.class).get3DArray()));
        System.out.println(path(root(TestBeanA.class).getLong().longValue()));
        System.out.println(path(root(TestBeanA.class).getInteger()));
        System.out.println(path(root(TestBeanA.class).getChild()));
        System.out.println(path(root(TestBeanA.class).getChild().getParent().getChild().getGrandChild()));
        root(TestBeanA.class).setData(Collections.EMPTY_LIST);
        System.out.println(path());
        System.out.println(path(expr(JButton.class).getAction().isEnabled()));
        System.out.println(path(root(JLabel.class).getAccessibleContext().getAccessibleAction()));
        System.out.println(path(root(TestBeanA.class).toString()));
        System.out.println(path(root(JLabel.class).toString()));
        System.out.println(path(root(TestBeanA.class).getChild().getCalendar().getTimeInMillis()));
        System.out.println(path(root(TestBeanA.class).getCalendar().getEon().getLowestSetBit()));
        System.out.println(path(root(TestBeanA.class).getClass().getAnnotations()));
    }

    public static abstract class TestBeanA {

        public List<String> getStrings() {
            return Collections.<String>emptyList();
        }

        public void setData(Collection<?> data) {

        }

        public Object[] getArray() {
            return null;
        }

        public Object[][] get2DArray() {
            return null;
        }

        public int[][] get3DArray() {
            return null;
        }

        public Long getLong() {
            return null;
        }

        public abstract Integer[] getInteger();

        public abstract TestBeanB getChild();

        public XMLGregorianCalendar getCalendar() {
            return null;
        }

    }

    public static class TestBeanB {

        public TestBeanA getParent() {
            return null;
        }

        public Object getGrandChild() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public GregorianCalendar getCalendar() {
            return null;
        }

    }

}
