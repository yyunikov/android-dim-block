/*
 * Copyright 2014 Yuriy Yunikov
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
package com.yyunikov.dimblock.base;

/**
 * Model class, that contains configurations and data that can be accessed in all application.
 *
 * @author yyunikov
 */
public class Model {

    private static volatile Model mInstance;

    private static DimBlockApplication mApplicationContext;

    private Config mConfig;

    private Model() {
    }

    public static Model getInstance() {
        if (mInstance == null) {
            mInstance = new Model();
        }
        return mInstance;
    }

    public void initialize(final DimBlockApplication applicationContext) {
        mApplicationContext = applicationContext;

        mConfig = new Config(applicationContext, new String[]{"config.properties"});
    }

    public DimBlockApplication getApplication() {
        return mApplicationContext;
    }

    public Config getConfiguration() {
        return mConfig;
    }
}
