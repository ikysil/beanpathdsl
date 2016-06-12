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
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
public final class DynamicBeanPath {

    private static final ThreadLocal<List<PropertyDescriptor>> CURRENT_PATH = new ThreadLocal<List<PropertyDescriptor>>() {

        @Override
        protected List<PropertyDescriptor> initialValue() {
            return new ArrayList<>();
        }

    };

    private DynamicBeanPath() {
    }

    /**
     *
     * @param <T>
     * @param clazz
     * @return
     */
    public static <T> T root(Class<T> clazz) {
        CURRENT_PATH.remove();
        return expr(clazz);
    }

    /**
     *
     * @param <T>
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T expr(Class<T> clazz) {
        try {
            T result;
            if (Modifier.isFinal(clazz.getModifiers())) {
                result = clazz.newInstance();
            }
            else {
                ProxyFactory pf = new ProxyFactory();
                if (clazz.isInterface()) {
                    pf.setSuperclass(Object.class);
                    pf.setInterfaces(new Class<?>[]{ clazz });
                }
                else {
                    pf.setSuperclass(clazz);
                }
                pf.setFilter(new DefaultMethodFilter());
                result = (T) pf.create(new Class<?>[0], new Object[0], new DefaultMethodHandler(clazz));
            }
            return result;
        }
        catch (InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException ex) {
            throw new IllegalStateException(String.format("Can't instantiate %s", clazz.getName()), ex);
        }
    }

    /**
     *
     * @param o
     * @return
     */
    public static String path(Object o) {
        return path();
    }

    /**
     *
     * @return
     */
    public static String path() {
        StringBuilder sb = new StringBuilder();
        List<PropertyDescriptor> pdPath = CURRENT_PATH.get();
        for (PropertyDescriptor pd : pdPath) {
            if (sb.length() != 0) {
                sb.append('.');
            }
            sb.append(pd.getName());
        }
        return sb.toString();
    }

    private static class DefaultMethodFilter implements MethodFilter {

        private final Set<String> ignoredMethods = new HashSet<>();

        public DefaultMethodFilter() {
            ignoredMethods.add("finalize");
        }

        @Override
        public boolean isHandled(Method m) {
            return !ignoredMethods.contains(m.getName());
        }

    }

    private static class DefaultMethodHandler implements MethodHandler {

        private final Map<String, PropertyDescriptor> methodNameToPropertyDescriptor = new HashMap<>();

        private static final Map<Class<?>, Object> PRIMITIVE_TYPE_INSTANCE = new HashMap<>();

        static {
            PRIMITIVE_TYPE_INSTANCE.put(Void.TYPE, Void.TYPE);
            PRIMITIVE_TYPE_INSTANCE.put(Void.class, Void.TYPE);
            PRIMITIVE_TYPE_INSTANCE.put(Character.TYPE, Character.valueOf(' '));
            PRIMITIVE_TYPE_INSTANCE.put(Character.class, Character.valueOf(' '));
            PRIMITIVE_TYPE_INSTANCE.put(Boolean.TYPE, Boolean.FALSE);
            PRIMITIVE_TYPE_INSTANCE.put(Boolean.class, Boolean.FALSE);
            PRIMITIVE_TYPE_INSTANCE.put(Byte.TYPE, Byte.valueOf((byte) 0));
            PRIMITIVE_TYPE_INSTANCE.put(Byte.class, Byte.valueOf((byte) 0));
            PRIMITIVE_TYPE_INSTANCE.put(Short.TYPE, Short.valueOf((short) 0));
            PRIMITIVE_TYPE_INSTANCE.put(Short.class, Short.valueOf((short) 0));
            PRIMITIVE_TYPE_INSTANCE.put(Integer.TYPE, Integer.valueOf(0));
            PRIMITIVE_TYPE_INSTANCE.put(Integer.class, Integer.valueOf(0));
            PRIMITIVE_TYPE_INSTANCE.put(Long.TYPE, Long.valueOf(0L));
            PRIMITIVE_TYPE_INSTANCE.put(Long.class, Long.valueOf(0L));
            PRIMITIVE_TYPE_INSTANCE.put(Float.TYPE, Float.valueOf(0));
            PRIMITIVE_TYPE_INSTANCE.put(Float.class, Float.valueOf(0));
            PRIMITIVE_TYPE_INSTANCE.put(Double.TYPE, Double.valueOf(0));
            PRIMITIVE_TYPE_INSTANCE.put(Double.class, Double.valueOf(0));
            PRIMITIVE_TYPE_INSTANCE.put(BigInteger.class, BigInteger.valueOf(0L));
            PRIMITIVE_TYPE_INSTANCE.put(BigDecimal.class, BigDecimal.valueOf(0L));
        }

        public DefaultMethodHandler(Class<?> sourceClass) {
            populatePropertyDescriptors(sourceClass);
        }

        private void addPropertyDescriptor(Method method, PropertyDescriptor pd) {
            if (method == null) {
                return;
            }
            methodNameToPropertyDescriptor.put(method.getName(), pd);
        }

        private void populatePropertyDescriptors(Class<?> sourceClass) {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] pds = propertyUtilsBean.getPropertyDescriptors(sourceClass);
            for (PropertyDescriptor pd : pds) {
                addPropertyDescriptor(pd.getReadMethod(), pd);
                addPropertyDescriptor(pd.getWriteMethod(), pd);
            }
        }

        @Override
        public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
            if (!extendCurrentPath(thisMethod)) {
                return proceed.invoke(self, args);
            }
            final Class<?> returnType = thisMethod.getReturnType();
            Object primitive = PRIMITIVE_TYPE_INSTANCE.get(returnType);
            if (primitive != null) {
                return primitive;
            }
            if (returnType.isArray()) {
                return Array.newInstance(returnType.getComponentType(), 0);
            }
            return expr(returnType);
        }

        private boolean extendCurrentPath(Method m) throws IllegalStateException {
            final String methodName = m.getName();
            PropertyDescriptor pd = methodNameToPropertyDescriptor.get(methodName);
            final boolean result = pd != null;
            if (result) {
                CURRENT_PATH.get().add(pd);
            }
            return result;
        }

    }

}