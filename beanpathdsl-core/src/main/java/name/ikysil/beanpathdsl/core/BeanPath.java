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
package name.ikysil.beanpathdsl.core;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
class BeanPath {

    public BeanPath(BeanPath parent, String propertyName) {
        this.parent = parent;
        this.propertyName = propertyName;
    }

    private final String propertyName;

    public String getPropertyName() {
        return propertyName;
    }

    private final BeanPath parent;

    /**
     * Get the value of parent
     *
     * @return the value of parent
     */
    public BeanPath getParent() {
        return parent;
    }

    @Override
    public String toString() {
        String parentPath = parent == null ? null : parent.toString();
        return (parentPath == null ? "" : parentPath + ".") + propertyName;
    }

}
