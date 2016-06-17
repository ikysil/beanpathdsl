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
package name.ikysil.beanpathdsl.codegen;

import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import name.ikysil.beanpathdsl.annotation.ExcludeClass;
import name.ikysil.beanpathdsl.annotation.ExcludePackage;
import name.ikysil.beanpathdsl.annotation.IncludeClass;
import name.ikysil.beanpathdsl.annotation.ScanPackage;
import name.ikysil.beanpathdsl.codegen.configuration.ExcludedClass;
import name.ikysil.beanpathdsl.codegen.configuration.ExcludedPackage;
import name.ikysil.beanpathdsl.codegen.configuration.IncludedClass;
import name.ikysil.beanpathdsl.codegen.configuration.ScannedPackage;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
public class Context {

    private final Logger logger = LoggerFactory.getLogger(Context.class);

    private final Reflections reflections = new Reflections(
            new FieldAnnotationsScanner(),
            new TypeAnnotationsScanner(),
            new SubTypesScanner(),
            new MethodAnnotationsScanner(),
            new MethodParameterScanner()
    );

    private final PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();

    public Context() {
    }

    /**
     * Get the value of reflections
     *
     * @return the value of reflections
     */
    public Reflections getReflections() {
        return reflections;
    }

    private final Map<Class<?>, ExcludedClass> excludedClasses = new HashMap<>();

    private final Collection<ExcludedPackage> excludedPackages = new ArrayList<>();

    private final Map<Class<?>, IncludedClass> includedClasses = new HashMap<>();

    private final Collection<ScannedPackage> scannedPackages = new ArrayList<>();

    public void scanAnnotatedElements() {
        Map<Class<?>, ExcludeClass> excludeClassesConfiguration = scanForAnnotation(ExcludeClass.class);
        for (Map.Entry<Class<?>, ExcludeClass> entry : excludeClassesConfiguration.entrySet()) {
            Class<?> clazz = entry.getKey();
            ExcludeClass config = entry.getValue();
            excludedClasses.put(clazz, new ExcludedClass(clazz, config));
        }
        Map<Class<?>, ExcludePackage> excludePackagesConfiguration = scanForAnnotation(ExcludePackage.class);
        for (Map.Entry<Class<?>, ExcludePackage> entry : excludePackagesConfiguration.entrySet()) {
            Class<?> clazz = entry.getKey();
            ExcludePackage config = entry.getValue();
            if (clazz.getPackage() != null) {
                excludedPackages.add(new ExcludedPackage(clazz.getPackage(), config));
            }
        }
        Map<Class<?>, IncludeClass> includeClassesConfiguration = scanForAnnotation(IncludeClass.class);
        for (Map.Entry<Class<?>, IncludeClass> entry : includeClassesConfiguration.entrySet()) {
            Class<?> clazz = entry.getKey();
            IncludeClass config = entry.getValue();
            includedClasses.put(clazz, new IncludedClass(clazz, config));
        }
        Map<Class<?>, ScanPackage> includePackagesConfiguration = scanForAnnotation(ScanPackage.class);
        for (Map.Entry<Class<?>, ScanPackage> entry : includePackagesConfiguration.entrySet()) {
            Class<?> clazz = entry.getKey();
            ScanPackage config = entry.getValue();
            if (clazz.getPackage() != null) {
                scannedPackages.add(new ScannedPackage(clazz.getPackage(), config));
            }
        }
        Map<Class<?>, name.ikysil.beanpathdsl.annotation.Configuration> configurations = scanForAnnotation(name.ikysil.beanpathdsl.annotation.Configuration.class);
        for (Map.Entry<Class<?>, name.ikysil.beanpathdsl.annotation.Configuration> entry : configurations.entrySet()) {
            Class<?> clazz = entry.getKey();
            includedClasses.put(clazz, new IncludedClass(clazz));
        }
    }

    private Class<?> resolveClass(Class<?> clazz) {
        if (clazz.isArray()) {
            return clazz.getComponentType();
        }
        return clazz;
    }

    private <A extends Annotation> Map<Class<?>, A> scanForAnnotation(Class<A> annotationClass) {
        Map<Class<?>, A> result = new HashMap<>();
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(annotationClass);
        for (Class<?> type : types) {
            result.put(resolveClass(type), type.getAnnotation(annotationClass));
        }
        Set<Field> fields = reflections.getFieldsAnnotatedWith(annotationClass);
        for (Field field : fields) {
            result.put(resolveClass(field.getType()), field.getAnnotation(annotationClass));
        }
        Set<Method> methods = reflections.getMethodsAnnotatedWith(annotationClass);
        for (Method method : methods) {
            scanMethod(result, method, annotationClass);
        }
        methods = reflections.getMethodsWithAnyParamAnnotated(annotationClass);
        for (Method method : methods) {
            scanMethod(result, method, annotationClass);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private <A extends Annotation> void scanMethod(Map<Class<?>, A> result, Method method, Class<A> annotationClass) {
        final A methodAnnotation = method.getAnnotation(annotationClass);
        if (methodAnnotation != null) {
            result.put(resolveClass(method.getReturnType()), methodAnnotation);
        }
        Class<?>[] paramTypes = method.getParameterTypes();
        Annotation[][] paramsAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < paramTypes.length; i++) {
            Annotation[] paramAnnotations = paramsAnnotations[i];
            A paramAnnotation = methodAnnotation;
            for (Annotation annotation : paramAnnotations) {
                if (annotation.annotationType().equals(annotationClass)) {
                    paramAnnotation = (A) annotation;
                    break;
                }
            }
            if (paramAnnotation != null) {
                result.put(resolveClass(paramTypes[i]), paramAnnotation);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Map<Class<?>, IncludedClass> scanForSubclasses() {
        final Set<String> includedPackageNames = new HashSet<>();
        for (ScannedPackage scannedPackage : scannedPackages) {
            includedPackageNames.add(scannedPackage.getPackageName());
        }
        final Set<URL> urls = new HashSet<>();
        for (Map.Entry<Class<?>, IncludedClass> entry : includedClasses.entrySet()) {
            Class<?> clazz = entry.getKey();
            IncludedClass config = entry.getValue();
            if (config.isWithSubclasses()) {
                urls.add(ClasspathHelper.forClass(clazz));
            }
        }
        final Reflections packageReflections = new Reflections(includedPackageNames, urls);
        final Map<Class<?>, IncludedClass> includedClassesHierarchy = new HashMap<>();
        for (Map.Entry<Class<?>, IncludedClass> entry : includedClasses.entrySet()) {
            Class<?> clazz = entry.getKey();
            IncludedClass config = entry.getValue();
            includedClassesHierarchy.put(clazz, config);
            if (config.isWithSubclasses()) {
                logger.info("Scanning for subclasses of: {} in {}", clazz, includedPackageNames);
                Set<Class<?>> packageClasses = (Set<Class<?>>) packageReflections.getSubTypesOf(clazz);
                for (Class<?> packageClass : packageClasses) {
                    logger.info("Subclass found: {}", packageClass);
                    includedClassesHierarchy.put(packageClass, config);
                }
            }
        }
        return includedClassesHierarchy;
    }

    private boolean isExcluded(Class<?> clazz) {
        if (clazz.isPrimitive() || clazz.isAnonymousClass() || clazz.isLocalClass() || !Modifier.isPublic(clazz.getModifiers())
                || (clazz.getPackage() == null)) {
            return true;
        }
        IncludedClass includedConfig = includedClasses.get(clazz);
        if (includedConfig != null) {
            return false;
        }
        ExcludedClass excludedConfig = excludedClasses.get(clazz);
        if (excludedConfig != null) {
            return true;
        }
        for (ExcludedPackage excludedPackage : excludedPackages) {
            if (excludedPackage.match(clazz.getPackage())) {
                return true;
            }
        }
        return false;
    }

    public Map<Class<?>, IncludedClass> buildTransitiveClosure() {
        Map<Class<?>, IncludedClass> includedClassesHierarchy = scanForSubclasses();
        final Map<Class<?>, IncludedClass> transitiveClosure = new HashMap<>();
        for (Map.Entry<Class<?>, IncludedClass> entry : includedClassesHierarchy.entrySet()) {
            Class<?> clazz = entry.getKey();
            IncludedClass config = entry.getValue();
            buildTransitiveClosure(transitiveClosure, clazz, config);
        }
        return transitiveClosure;
    }

    private void buildTransitiveClosure(Map<Class<?>, IncludedClass> transitiveClosure, Class<?> clazz, IncludedClass config) {
        if (transitiveClosure.containsKey(clazz) || isExcluded(clazz)) {
            return;
        }
        logger.info("Class: {}", clazz);
        transitiveClosure.put(clazz, config);
        if (config.isTransitive()) {
            PropertyDescriptor[] pds = getPropertyDescriptors(clazz);
            for (PropertyDescriptor pd : pds) {
                logger.debug("Property Descriptor: {} :: {}", clazz, pd);
                Class<?> pdClass = pd.getPropertyType();
                if ((pdClass == null) && (pd instanceof IndexedPropertyDescriptor)) {
                    pdClass = ((IndexedPropertyDescriptor) pd).getIndexedPropertyType();
                }
                if (pdClass == null) {
                    continue;
                }
                IncludedClass pdConfig = transitiveClosure.get(pdClass);
                if (pdConfig == null) {
                    pdConfig = config;
                }
                buildTransitiveClosure(transitiveClosure, pdClass, pdConfig);
            }
        }
    }

    public PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) {
        return propertyUtilsBean.getPropertyDescriptors(clazz);
    }

}
