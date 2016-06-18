/*
* Copyright (C) 2014 The Android Open Source Project
* Modifications Copyright (C) 2016 Fred Grott(GrottWorkShop)
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.github.shareme.gwsmaterialmatters.core.util;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.view.View;

/**
 * These classes borrowed from Chris Banes
 * https://gist.github.com/chrisbanes/73de18faffca571f7292
 * Created by fgrott on 6/18/2016.
 */

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressWarnings("unused")
class SystemUIHelperImplJB extends SystemUIHelperImplICS {

  SystemUIHelperImplJB(Activity activity, int level, int flags,
                       SystemUIHelper.OnVisibilityChangeListener onVisibilityChangeListener) {
    super(activity, level, flags, onVisibilityChangeListener);
  }


  @Override
  protected int createShowFlags() {
    int flag = super.createShowFlags();


    if (mLevel >= SystemUIHelper.LEVEL_HIDE_STATUS_BAR) {
      flag |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;


      if (mLevel >= SystemUIHelper.LEVEL_LEAN_BACK) {
        flag |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
      }
    }


    return flag;
  }


  @Override
  protected int createHideFlags() {
    int flag = super.createHideFlags();


    if (mLevel >= SystemUIHelper.LEVEL_HIDE_STATUS_BAR) {
      flag |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE
              | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
              | View.SYSTEM_UI_FLAG_FULLSCREEN;


      if (mLevel >= SystemUIHelper.LEVEL_LEAN_BACK) {
        flag |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
      }
    }


    return flag;
  }


  @Override
  protected void onSystemUiShown() {
    if (mLevel == SystemUIHelper.LEVEL_LOW_PROFILE) {
      // Manually show the action bar when in low profile mode.
      ActionBar ab = mActivity.getActionBar();
      if (ab != null) {
        ab.show();
      }
    }


    setIsShowing(true);
  }


  @Override
  protected void onSystemUiHidden() {
    if (mLevel == SystemUIHelper.LEVEL_LOW_PROFILE) {
      // Manually hide the action bar when in low profile mode.
      ActionBar ab = mActivity.getActionBar();
      if (ab != null) {
        ab.hide();
      }
    }


    setIsShowing(false);
  }
}
