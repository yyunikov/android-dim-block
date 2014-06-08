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

import android.content.Context;
import android.util.Log;

/**
 * @author yyunikov
 */
public class Logger {

    private static final String LOG_TAG = "DimBlock";

    public static void debug(final String text) {
        Log.d(LOG_TAG, text);
    }

    public static void error(final String text) {
        error(text, null);
    }

    public static void error(final String text, final Context context) {
        Log.e(LOG_TAG, text);

        if (context != null) {
            Analytics.getInstance().sendException(context, new Exception(text));
        }
    }

    public static void error(final String text, final Context context, final Exception e) {
        Log.e(LOG_TAG, text);

        if (context != null) {
            Analytics.getInstance().sendException(context, e);
        }
    }
}
