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

    private static final ThreadLocal<BeanExpr> CURRENT_PATH = new ThreadLocal<>();

    /**
     * Reset current bean path.
     *
     */
    protected static void resetCurrentPath() {
        CURRENT_PATH.remove();
    }

    protected static void extendCurrentPath(String propertyName) {
        CURRENT_PATH.set(new BeanExpr(CURRENT_PATH.get(), propertyName));
    }

    /**
     * Return the current bean path.
     *
     * @return current bean path
     */
    public String $$path() {
        BeanExpr beanExpr = CURRENT_PATH.get();
        return beanExpr == null ? null : beanExpr.toString();
    }

    @Override
    public String toString() {
        return $$path();
    }

}
