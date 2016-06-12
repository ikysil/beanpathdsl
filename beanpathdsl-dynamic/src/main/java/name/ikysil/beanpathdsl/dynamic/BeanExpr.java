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
package name.ikysil.beanpathdsl.dynamic;

import java.beans.PropertyDescriptor;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
class BeanExpr {

    private BeanExpr parent;

    /**
     * Get the value of parent
     *
     * @return the value of parent
     */
    public BeanExpr getParent() {
        return parent;
    }

    /**
     * Set the value of parent
     *
     * @param parent new value of parent
     */
    public void setParent(BeanExpr parent) {
        this.parent = parent;
    }

    private PropertyDescriptor property;

    /**
     * Get the value of property
     *
     * @return the value of property
     */
    public PropertyDescriptor getProperty() {
        return property;
    }

    /**
     * Set the value of property
     *
     * @param property new value of property
     */
    public void setProperty(PropertyDescriptor property) {
        this.property = property;
    }

}
