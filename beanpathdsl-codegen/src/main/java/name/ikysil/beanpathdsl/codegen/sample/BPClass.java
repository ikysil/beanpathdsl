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

import name.ikysil.beanpathdsl.core.BaseNavigator;
import name.ikysil.beanpathdsl.core.annotations.Navigator;

/**
 *
 * @author Illya Kysil <ikysil@ikysil.name>
 */
@Navigator(Class.class)
public class BPClass extends BaseNavigator {

    public static final BPClass INSTANCE = new BPClass();

    private BPClass() {
    }

}
