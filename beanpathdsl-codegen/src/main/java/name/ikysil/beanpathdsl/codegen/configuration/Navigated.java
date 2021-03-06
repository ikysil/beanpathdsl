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

import name.ikysil.beanpathdsl.core.annotations.Navigator;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
public class Navigated {

    private final Class<?> navigatedClass;

    private final Class<?> navigatorClass;

    public Navigated(Navigator config, Class<?> navigatorClass) {
        this(config.value(), navigatorClass);
    }

    public Navigated(Class<?> navigatedClass, Class<?> navigatorClass) {
        this.navigatedClass = navigatedClass;
        this.navigatorClass = navigatorClass;
    }

    /**
     * Get the value of navigatedClass
     *
     * @return the value of navigatedClass
     */
    public Class<?> getNavigatedClass() {
        return navigatedClass;
    }

    /**
     * Get the value of navigatorClass
     *
     * @return the value of navigatorClass
     */
    public Class<?> getNavigatorClass() {
        return navigatorClass;
    }

}
