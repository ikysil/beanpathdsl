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
public class BeanPath {

    private static final BeanPath INSTANCE = new BeanPath();

    public static BeanPath getInstance() {
        return INSTANCE;
    }

    /**
     * Reset current bean path.
     *
     */
    protected static void resetCurrentPath() {
        BeanPaths.reset();
    }

    protected static void extendCurrentPath(String propertyName) {
        BeanPaths.extend(propertyName);
    }

    @Override
    public String toString() {
        return BeanPaths.path();
    }

    /**
     * Reinterpret the property at current beanpath as a specified beanpath bean.
     *
     * @param <T> target beanpath type
     * @param expr beanpath bean
     * @return beanpath bean
     */
    public <T extends BeanPath> T as(T expr) {
        return expr;
    }

}
