/*
 * Copyright 2017 Illya Kysil <ikysil@ikysil.name>.
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
package name.ikysil.beanpathdsl.dynamic;

import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.Test;

import static name.ikysil.beanpathdsl.dynamic.DynamicBeanPath.expr;
import static name.ikysil.beanpathdsl.dynamic.DynamicBeanPath.path;
import static name.ikysil.beanpathdsl.dynamic.DynamicBeanPath.root;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
public class DynamicBeanPathTest {

    @Test
    public void successWhenNavigating() {
        System.out.println("successWhenNavigating");
        assertThat(path(root(TestBeanA.class).getStrings().isEmpty()), equalTo("strings.empty"));
        assertThat(path(root(TestBeanA.class).getArray()), equalTo("array"));
        assertThat(path(root(TestBeanA.class).get2DArray()), equalTo("2DArray"));
        assertThat(path(root(TestBeanA.class).get3DArray()), equalTo("3DArray"));
        // java.lang.Long is final
        assertThat(path(root(TestBeanA.class).getLong().longValue()), equalTo("long"));
        assertThat(path(root(TestBeanA.class).getInteger()), equalTo("integer"));
        assertThat(path(root(TestBeanA.class).getChild()), equalTo("child"));
        assertThat(path(root(TestBeanA.class).getChild().getParent().getChild().getGrandChild()), equalTo("child.parent.child.grandChild"));
        root(TestBeanA.class).setData(Collections.EMPTY_LIST);
        assertThat(path(), equalTo("data"));
        // expr() carries over the existing path
        assertThat(path(expr(JButton.class).getAction().isEnabled()), equalTo("data.action.enabled"));
        assertThat(path(root(JLabel.class).getAccessibleContext().getAccessibleAction()), equalTo("accessibleContext.accessibleAction"));
        assertThat(path(root(TestBeanA.class).getChild().getCalendar().getTimeInMillis()), equalTo("child.calendar.timeInMillis"));
        // getEon() returns java.math.BigInteger which has no no-arg constructor
        assertThat(path(root(TestBeanA.class).getCalendar().getEon().getLowestSetBit()), equalTo("calendar.eon"));
        // java.lang.Class is final
        assertThat(path(root(TestBeanA.class).getClass().getAnnotations()), nullValue());
        // TestBeanA.toString() is not overriden
        assertThat(path(root(TestBeanA.class).toString()), nullValue());
        // JLabel.toString() invokes getName()
        assertThat(path(root(JLabel.class).toString()), equalTo("name"));
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
