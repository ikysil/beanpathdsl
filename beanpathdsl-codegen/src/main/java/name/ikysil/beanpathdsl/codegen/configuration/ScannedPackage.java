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

import name.ikysil.beanpathdsl.core.annotations.ScanPackage;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
public class ScannedPackage {

    public ScannedPackage(Package aPackage, ScanPackage config) {
        this.packageName = aPackage.getName();
    }

    private String packageName;

    /**
     * Get the value of packageName
     *
     * @return the value of packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Set the value of packageName
     *
     * @param packageName new value of packageName
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

}
