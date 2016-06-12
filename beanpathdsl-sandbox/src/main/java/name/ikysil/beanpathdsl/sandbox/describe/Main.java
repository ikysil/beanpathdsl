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
package name.ikysil.beanpathdsl.sandbox.describe;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        Reflections reflections = new Reflections(TestBean.class, new SubTypesScanner(false));
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        for (Class<?> clazz : classes) {
            System.out.println(String.format("Class name: %s", clazz.getName()));
            System.out.println(String.format("Class simple name: %s", clazz.getSimpleName()));
            System.out.println(String.format("Class canonical name: %s", clazz.getCanonicalName()));
            PropertyDescriptor[] pds = propertyUtilsBean.getPropertyDescriptors(clazz);
            for (PropertyDescriptor pd : pds) {
                System.out.println(String.format("    Property name: %s", pd.getName()));
                Class<?> pc = pd.getPropertyType();
                System.out.println(String.format("    Class name: %s", pc.getName()));
                System.out.println(String.format("    Class simple name: %s", pc.getSimpleName()));
                System.out.println(String.format("    Class canonical name: %s", pc.getCanonicalName()));
            }
        }
    }

    public static class TestBean {

        public List<String> getStrings() {
            return Collections.<String>emptyList();
        }

        public void setData(Collection<?> data) {

        }

    }

}
