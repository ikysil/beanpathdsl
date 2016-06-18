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

import java.nio.charset.Charset;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
class Configuration {

    public static final String DEFAULT_CLASS_NAME_PREFIX = "BP";

    public static final String DEFAULT_PACKAGE_NAME_PREFIX = "beanpath";

    public static final String DEFAULT_PACKAGE_NAME_SUFFIX = "";

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF8");

    private String outputDirectory;

    /**
     * Get the value of outputDirectory
     *
     * @return the value of outputDirectory
     */
    public String getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * Set the value of outputDirectory
     *
     * @param outputDirectory new value of outputDirectory
     */
    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    private Charset outputCharset = DEFAULT_CHARSET;

    /**
     * Get the value of outputCharset
     *
     * @return the value of outputCharset
     */
    public Charset getOutputCharset() {
        return outputCharset;
    }

    /**
     * Set the value of outputCharset
     *
     * @param outputCharset new value of outputCharset
     */
    public void setOutputCharset(Charset outputCharset) {
        this.outputCharset = outputCharset;
    }

    private String classNamePrefix = DEFAULT_CLASS_NAME_PREFIX;

    /**
     * Get the value of classNamePrefix
     *
     * @return the value of classNamePrefix
     */
    public String getClassNamePrefix() {
        return classNamePrefix;
    }

    /**
     * Set the value of classNamePrefix
     *
     * @param classNamePrefix new value of classNamePrefix
     */
    public void setClassNamePrefix(String classNamePrefix) {
        this.classNamePrefix = classNamePrefix;
    }

    private String classNameSuffix;

    /**
     * Get the value of classNameSuffix
     *
     * @return the value of classNameSuffix
     */
    public String getClassNameSuffix() {
        return classNameSuffix;
    }

    /**
     * Set the value of classNameSuffix
     *
     * @param classNameSuffix new value of classNameSuffix
     */
    public void setClassNameSuffix(String classNameSuffix) {
        this.classNameSuffix = classNameSuffix;
    }

    private String packageNameSuffix = DEFAULT_PACKAGE_NAME_SUFFIX;

    /**
     * Get the value of packageNameSuffix
     *
     * @return the value of packageNameSuffix
     */
    public String getPackageNameSuffix() {
        return packageNameSuffix;
    }

    /**
     * Set the value of packageNameSuffix
     *
     * @param packageNameSuffix new value of packageNameSuffix
     */
    public void setPackageNameSuffix(String packageNameSuffix) {
        this.packageNameSuffix = packageNameSuffix;
    }

    private String packageNamePrefix = DEFAULT_PACKAGE_NAME_PREFIX;

    /**
     * Get the value of packageNamePrefix
     *
     * @return the value of packageNamePrefix
     */
    public String getPackageNamePrefix() {
        return packageNamePrefix;
    }

    /**
     * Set the value of packageNamePrefix
     *
     * @param packageNamePrefix new value of packageNamePrefix
     */
    public void setPackageNamePrefix(String packageNamePrefix) {
        this.packageNamePrefix = packageNamePrefix;
    }

}
