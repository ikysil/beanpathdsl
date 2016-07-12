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
public class BeanPaths {

    private static final ThreadLocal<BeanPath> CURRENT_PATH = new ThreadLocal<>();

    /**
     * Reset current bean path.
     *
     */
    static void reset() {
        CURRENT_PATH.remove();
    }

    static void navigate(String propertyName) {
        CURRENT_PATH.set(new BeanPath(CURRENT_PATH.get(), propertyName));
    }

    /**
     * Reset current bean path and start from a specified beanpath bean.
     *
     * @param <T> type of the beanpath bean
     * @param root beanpath bean
     * @return beanpath bean
     */
    public static <T extends BeanPathNavigator> T root(T root) {
        reset();
        return expr(root);
    }

    /**
     * Continue current bean path from a specified beanpath bean.
     *
     * @param <T> type of the beanpath bean
     * @param expr beanpath bean
     * @return beanpath bean
     */
    public static <T extends BeanPathNavigator> T expr(T expr) {
        return expr;
    }

    /**
     * Reinterpret the property at current beanpath as a specified beanpath bean.
     *
     * @param <T> target beanpath type
     * @param beanPath current beanpath, ignored
     * @param expr beanpath bean
     * @return beanpath bean
     */
    public static <T extends BeanPathNavigator> T as(BeanPathNavigator beanPath, T expr) {
        return expr(expr);
    }

    /**
     * Return the current bean path. Parameter is ignored.
     *
     * @param o ignored
     * @return current bean path
     */
    public static String path(Object o) {
        return path();
    }

    /**
     * Return the current bean path.
     *
     * @return current bean path
     */
    public static String path() {
        BeanPath beanExpr = CURRENT_PATH.get();
        return beanExpr == null ? null : beanExpr.toString();
    }

}
