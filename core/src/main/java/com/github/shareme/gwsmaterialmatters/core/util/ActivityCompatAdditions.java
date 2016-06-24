/*
 * Copyright (C) 2016 Fred Grott(aka shareme GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under License.
 */
package com.github.shareme.gwsmaterialmatters.core.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

/**
 * Helper to correctly call the new reportFullyDrawn api 19 call in a backwards and
 * forwards compatible fashion. Call:
 *
 * <code>
 *   ActivityCompatAdditions.reportFullyDrawn(this);
 * </code>
 *
 * goes in the onCreate or onLoadFinished block as the last call.
 * Borrowed from CyrilMottier's Launch Screens-From A Tap to your App talk slides.
 *
 * Created by fgrott on 6/22/2016.
 */
@SuppressWarnings("unused")
public final class ActivityCompatAdditions {

  private static final Impl IMPL;

  static {
    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
      IMPL = new LollipopImpl();
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
      IMPL = new KitKatImpl();
    } else {
      IMPL = new BaseImpl();
    }
  }

  private ActivityCompatAdditions() {
    // no instances
  }

  public static void reportfullyDrawn(Activity activity){
    IMPL.reportFullyDrawn(activity);
  }

  private interface Impl {
    void reportFullyDrawn(Activity activity);
  }

  private static final class BaseImpl implements Impl {

    @Override
    public void reportFullyDrawn(Activity activity){
      // no op
    }
  }

  private static final class KitKatImpl implements Impl {

    @Override @TargetApi(Build.VERSION_CODES.KITKAT)
    public void reportFullyDrawn(Activity activity){
      try {
        activity.reportFullyDrawn();
      } catch (SecurityException se){
        // prevent an exception on kitkat
      }
    }
  }

  private static final class LollipopImpl implements Impl {
    @Override@TargetApi(Build.VERSION_CODES.KITKAT)
    public void reportFullyDrawn(Activity activity){
      activity.reportFullyDrawn();
    }
  }
}
