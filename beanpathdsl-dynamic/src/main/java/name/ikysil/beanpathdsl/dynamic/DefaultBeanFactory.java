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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
public final class DefaultBeanFactory implements BeanFactory {

    private static final Map<Class<?>, Object> TYPE_INSTANCE = new HashMap<>();

    static {
        TYPE_INSTANCE.put(Void.TYPE, Void.TYPE);
        TYPE_INSTANCE.put(Void.class, Void.TYPE);
        TYPE_INSTANCE.put(Character.TYPE, Character.valueOf(' '));
        TYPE_INSTANCE.put(Character.class, Character.valueOf(' '));
        TYPE_INSTANCE.put(Boolean.TYPE, Boolean.FALSE);
        TYPE_INSTANCE.put(Boolean.class, Boolean.FALSE);
        TYPE_INSTANCE.put(Byte.TYPE, Byte.valueOf((byte) 0));
        TYPE_INSTANCE.put(Byte.class, Byte.valueOf((byte) 0));
        TYPE_INSTANCE.put(Short.TYPE, Short.valueOf((short) 0));
        TYPE_INSTANCE.put(Short.class, Short.valueOf((short) 0));
        TYPE_INSTANCE.put(Integer.TYPE, Integer.valueOf(0));
        TYPE_INSTANCE.put(Integer.class, Integer.valueOf(0));
        TYPE_INSTANCE.put(Long.TYPE, Long.valueOf(0L));
        TYPE_INSTANCE.put(Long.class, Long.valueOf(0L));
        TYPE_INSTANCE.put(Float.TYPE, Float.valueOf(0));
        TYPE_INSTANCE.put(Float.class, Float.valueOf(0));
        TYPE_INSTANCE.put(Double.TYPE, Double.valueOf(0));
        TYPE_INSTANCE.put(Double.class, Double.valueOf(0));
        TYPE_INSTANCE.put(BigInteger.class, BigInteger.valueOf(0L));
        TYPE_INSTANCE.put(BigDecimal.class, BigDecimal.valueOf(0L));
    }

    @Override
    public Object createInstance(Class<?> clazz) {
        return TYPE_INSTANCE.get(clazz);
    }

}
