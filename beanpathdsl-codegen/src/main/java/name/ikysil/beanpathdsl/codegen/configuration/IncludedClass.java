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
package name.ikysil.beanpathdsl.codegen.configuration;

import name.ikysil.beanpathdsl.annotation.IncludeClass;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
public class IncludedClass {

    public IncludedClass(Class<?> clazz) {
        this.clazz = clazz;
        this.transitive = true;
    }

    public IncludedClass(Class<?> clazz, IncludeClass config) {
        this.clazz = clazz;
        this.transitive = config.transitive();
    }

    private Class<?> clazz;

    /**
     * Get the value of clazz
     *
     * @return the value of clazz
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * Set the value of clazz
     *
     * @param clazz new value of clazz
     */
    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    private boolean transitive;

    /**
     * Get the value of transitive
     *
     * @return the value of transitive
     */
    public boolean isTransitive() {
        return transitive;
    }

    /**
     * Set the value of transitive
     *
     * @param transitive new value of transitive
     */
    public void setTransitive(boolean transitive) {
        this.transitive = transitive;
    }

}
