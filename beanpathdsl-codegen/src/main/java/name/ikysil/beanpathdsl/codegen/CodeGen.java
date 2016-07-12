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

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.lang.model.element.Modifier;
import name.ikysil.beanpathdsl.codegen.configuration.IncludedClass;
import name.ikysil.beanpathdsl.codegen.configuration.Navigated;
import name.ikysil.beanpathdsl.core.BaseNavigator;
import name.ikysil.beanpathdsl.core.annotations.Navigator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
class CodeGen {

    private final Logger logger = LoggerFactory.getLogger(CodeGen.class);

    public static final String SOURCE_CLASS_FIELD_NAME = "SOURCE_CLASS";

    private final Configuration configuration;

    public CodeGen(Configuration configuration) {
        this.configuration = configuration;
    }

    public void process() {
        Context context = new Context();
        context.scanAnnotatedElements();
        final Map<Class<?>, IncludedClass> transitiveClosure = context.buildTransitiveClosure();
        final Map<Class<?>, Navigated> knownNavigators = context.getKnownNavigators();
        buildClassNames(transitiveClosure, knownNavigators);
        for (Map.Entry<Class<?>, IncludedClass> entry : transitiveClosure.entrySet()) {
            Class<?> clazz = entry.getKey();
            if (!knownNavigators.containsKey(clazz)) {
                generateBeanPathSource(context, transitiveClosure, clazz);
            }
        }
    }

    private final Map<Class<?>, ClassName> classNames = new HashMap<>();

    private void buildClassNames(Map<Class<?>, IncludedClass> transitiveClosure, Map<Class<?>, Navigated> knownNavigators) {
        for (Map.Entry<Class<?>, IncludedClass> entry : transitiveClosure.entrySet()) {
            Class<?> clazz = entry.getKey();
            Navigated knownNavigator = knownNavigators.get(clazz);
            ClassName className = null;
            if (knownNavigator == null) {
                String targetPackageName = getTargetPackageName(clazz);
                String targetClassName = getTargetClassName(clazz);
                className = ClassName.get(targetPackageName, targetClassName);
            }
            else {
                className = ClassName.get(knownNavigator.getNavigatorClass());
            }
            classNames.put(clazz, className);
        }
    }

    private String getTargetPackageName(Class<?> clazz) {
        final String packageName = clazz.getPackage().getName();
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isBlank(configuration.getPackageNamePrefix())) {
            sb.append(configuration.getPackageNamePrefix()).append(".");
        }
        sb.append(packageName);
        if (!StringUtils.isBlank(configuration.getPackageNameSuffix())) {
            sb.append(".").append(configuration.getPackageNameSuffix());
        }
        return sb.toString();
    }

    private String getTargetClassName(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isBlank(configuration.getClassNamePrefix())) {
            sb.append(configuration.getClassNamePrefix());
        }
        sb.append(ClassName.get(clazz).simpleName());
        if (!StringUtils.isBlank(configuration.getClassNameSuffix())) {
            sb.append(configuration.getClassNameSuffix());
        }
        String result = sb.toString();
        return result;
    }

    private String getInstanceAccessor(Class<?> clazz) {
        if (BaseNavigator.class.equals(clazz)) {
            return "getInstance()";
        }
        else {
            return "INSTANCE";
        }
    }

    private void generateBeanPathSource(Context context, Map<Class<?>, IncludedClass> transitiveClosure, Class<?> clazz) {
        ClassName targetClassName = classNames.get(clazz);
        logger.info("Generating {} for {}", targetClassName, clazz.getName());

        AnnotationSpec generatedSpec = AnnotationSpec.builder(Generated.class)
                .addMember("value", "$S", String.format("Generated by beanpath-codegen for %s", ClassName.get(clazz)))
                .build();

        AnnotationSpec navigatorSpec = AnnotationSpec.builder(Navigator.class)
                .addMember("value", "$T.class", clazz)
                .build();

        PropertyDescriptor[] pds = context.getPropertyDescriptors(clazz);
        Arrays.sort(pds, new Comparator<PropertyDescriptor>() {

            @Override
            public int compare(PropertyDescriptor o1, PropertyDescriptor o2) {
                return o1.getName().compareTo(o2.getName());
            }

        });

        Collection<FieldSpec> fieldSpecs = new ArrayList<>();
        Collection<MethodSpec> methodSpecs = new ArrayList<>();

        FieldSpec fieldSpec = FieldSpec.builder(targetClassName, getInstanceAccessor(clazz))
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T()", targetClassName)
                .build();
        fieldSpecs.add(fieldSpec);

        final ParameterizedTypeName fieldTypeName = ParameterizedTypeName.get(ClassName.get(Class.class), ClassName.get(clazz));
        fieldSpec = FieldSpec.builder(fieldTypeName, SOURCE_CLASS_FIELD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$T.class", clazz)
                .build();
        fieldSpecs.add(fieldSpec);

        for (PropertyDescriptor pd : pds) {
            String name = pd.getName();
            if (!Character.isJavaIdentifierStart(name.charAt(0))) {
                name = "_" + name;
            }
            if ("class".equals(name)) {
                name = "_" + name;
            }
            Class<?> pdClass = pd.getPropertyType();
            if ((pdClass == null) && (pd instanceof IndexedPropertyDescriptor)) {
                pdClass = ((IndexedPropertyDescriptor) pd).getIndexedPropertyType();
            }
            ClassName bpAccessorClassName = classNames.get(pdClass);
            if (bpAccessorClassName == null) {
                pdClass = BaseNavigator.class;
                bpAccessorClassName = ClassName.get(pdClass);
            }
            MethodSpec pdMethodSpec = MethodSpec.methodBuilder(name)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .returns(bpAccessorClassName)
                    .addCode(CodeBlock.builder()
                            .addStatement("navigate($S)", pd.getName())
                            .addStatement("return $T.$N", bpAccessorClassName, getInstanceAccessor(pdClass))
                            .build()
                    )
                    .build();
            methodSpecs.add(pdMethodSpec);

            pdMethodSpec = MethodSpec.methodBuilder(name)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addParameter(ClassName.get(String.class), "propertyName", Modifier.FINAL)
                    .returns(bpAccessorClassName)
                    .addCode(CodeBlock.builder()
                            .addStatement("navigate($N)", "propertyName")
                            .addStatement("return $T.$N", bpAccessorClassName, getInstanceAccessor(pdClass))
                            .build()
                    )
                    .build();
            methodSpecs.add(pdMethodSpec);
        }

        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .build();
        methodSpecs.add(constructor);

        TypeSpec typeSpec = TypeSpec.classBuilder(targetClassName)
                .superclass(ClassName.get(BaseNavigator.class))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(generatedSpec)
                .addAnnotation(navigatorSpec)
                .addFields(fieldSpecs)
                .addMethods(methodSpecs)
                .build();

        JavaFile javaFile = JavaFile.builder(targetClassName.packageName(), typeSpec)
                .skipJavaLangImports(true)
                .indent("    ")
                .build();

        try {
            javaFile.writeTo(Paths.get(configuration.getOutputDirectory()));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
