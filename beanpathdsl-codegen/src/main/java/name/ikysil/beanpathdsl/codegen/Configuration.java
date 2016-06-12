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
import java.util.List;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
public class Configuration {

    public static final String DEFAULT_CLASS_NAME_PREFIX = "BP";

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF8");

    private List<String> includedPackages;

    /**
     * Get the value of includedPackages
     *
     * @return the value of includedPackages
     */
    public List<String> getIncludedPackages() {
        return includedPackages;
    }

    /**
     * Set the value of includedPackages
     *
     * @param includedPackages new value of includedPackages
     */
    public void setIncludedPackages(List<String> includedPackages) {
        this.includedPackages = includedPackages;
    }

    private List<String> excludedPackages;

    /**
     * Get the value of excludedPackages
     *
     * @return the value of excludedPackages
     */
    public List<String> getExcludedPackages() {
        return excludedPackages;
    }

    /**
     * Set the value of excludedPackages
     *
     * @param excludedPackages new value of excludedPackages
     */
    public void setExcludedPackages(List<String> excludedPackages) {
        this.excludedPackages = excludedPackages;
    }

    private List<String> includedClasses;

    /**
     * Get the value of includedClasses
     *
     * @return the value of includedClasses
     */
    public List<String> getIncludedClasses() {
        return includedClasses;
    }

    /**
     * Set the value of includedClasses
     *
     * @param includedClasses new value of includedClasses
     */
    public void setIncludedClasses(List<String> includedClasses) {
        this.includedClasses = includedClasses;
    }

    private List<String> excludedClasses;

    /**
     * Get the value of excludedClasses
     *
     * @return the value of excludedClasses
     */
    public List<String> getExcludedClasses() {
        return excludedClasses;
    }

    /**
     * Set the value of excludedClasses
     *
     * @param excludedClasses new value of excludedClasses
     */
    public void setExcludedClasses(List<String> excludedClasses) {
        this.excludedClasses = excludedClasses;
    }

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

    private String packageNameSuffix;

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

}