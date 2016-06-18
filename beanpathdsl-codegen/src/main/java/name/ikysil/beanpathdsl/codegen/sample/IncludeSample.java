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
package name.ikysil.beanpathdsl.codegen.sample;

import java.beans.EventSetDescriptor;
import javax.swing.JLabel;
import name.ikysil.beanpathdsl.core.annotations.IncludeClass;
import name.ikysil.beanpathdsl.core.annotations.ScanPackage;
import name.ikysil.beanpathdsl.codegen.configuration.IncludedClass;
import org.apache.commons.beanutils.DynaClass;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
public interface IncludeSample {

    @IncludeClass
    IncludedClass getIncludedClass();

    @IncludeClass
    JLabel jLabel = null;

    @IncludeClass
    @ScanPackage
    void includeClasses(@IncludeClass(withSubclasses = true) java.beans.FeatureDescriptor featureDescriptor, EventSetDescriptor eventSetDescriptor);

    @ScanPackage
    javax.lang.model.SourceVersion getSourceVersion();

    @IncludeClass(withSubclasses = true)
    @ScanPackage
    void includeSubclasses(java.awt.Component c, javax.swing.JComponent jc);

    @IncludeClass(withSubclasses = true)
    @ScanPackage
    void includeBeanUtils(DynaClass dynaClass);

    @IncludeClass
    public class MemberL2 {

        public class MemberL3 {

        }

    }

}
