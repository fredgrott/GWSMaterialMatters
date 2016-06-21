/*
 * Copyright 2013 Inmite s.r.o. (www.inmite.eu)
 * Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.github.shareme.gwsmaterialmatters.core.mdialogs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by fgrott on 6/21/2016.
 */
@SuppressWarnings("unused")
public class CheckableLinearLayout extends LinearLayout implements Checkable {
  private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

  private Set<Checkable> mCheckablesSet = new HashSet<>();
  private boolean mChecked;

  public CheckableLinearLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    // find checkable items
    int childCount = getChildCount();
    for (int i = 0; i < childCount; ++i) {
      View v = getChildAt(i);
      if (v instanceof Checkable) {
        mCheckablesSet.add((Checkable) v);
      }
    }
  }

  @Override
  public boolean isChecked() {
    return mChecked;
  }

  @Override
  public void setChecked(boolean checked) {
    if (checked == this.mChecked) {
      return;
    }
    this.mChecked = checked;
    for (Checkable checkable : mCheckablesSet) {
      checkable.setChecked(checked);
    }
    refreshDrawableState();
  }

  @Override
  public void toggle() {
    setChecked(!mChecked);
  }


  public int[] onCreateDrawableState(int extraSpace) {
    final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
    if (isChecked()) {
      mergeDrawableStates(drawableState, CHECKED_STATE_SET);
    }
    return drawableState;
  }
}