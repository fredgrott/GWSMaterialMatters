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

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

/**
 * Dialog for displaying simple message, message with title or message with title and two buttons.
 * Implement {@link SimpleDialogListener} in your Fragment or Activity to rect on
 * positive and negative button clicks. This class can be extended and more
 * parameters can be added in overridden build() method.
 * Created by fgrott on 6/21/2016.
 */
@SuppressWarnings("unused")
public class SimpleDialogFragment extends BaseDialogFragment {

  protected final static String ARG_MESSAGE = "message";
  protected final static String ARG_TITLE = "title";
  protected final static String ARG_POSITIVE_BUTTON = "positive_button";
  protected final static String ARG_NEGATIVE_BUTTON = "negative_button";
  protected final static String ARG_NEUTRAL_BUTTON = "neutral_button";


  public static SimpleDialogBuilder createBuilder(Context context, FragmentManager fragmentManager) {
    return new SimpleDialogBuilder(context, fragmentManager, SimpleDialogFragment.class);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

  }

  /**
   * Key method for extending {@link SimpleDialogFragment}.
   * Children can extend this to add more things to base builder.
   */
  @Override
  protected BaseDialogFragment.Builder build(BaseDialogFragment.Builder builder) {
    final CharSequence title = getTitle();
    if (!TextUtils.isEmpty(title)) {
      builder.setTitle(title);
    }

    final CharSequence message = getMessage();
    if (!TextUtils.isEmpty(message)) {
      builder.setMessage(message);
    }

    final CharSequence positiveButtonText = getPositiveButtonText();
    if (!TextUtils.isEmpty(positiveButtonText)) {
      builder.setPositiveButton(positiveButtonText, new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          for (PositiveButtonDialogListener listener : getPositiveButtonDialogListeners()) {
            listener.onPositiveButtonClicked(mRequestCode);
          }
          dismiss();
        }
      });
    }

    final CharSequence negativeButtonText = getNegativeButtonText();
    if (!TextUtils.isEmpty(negativeButtonText)) {
      builder.setNegativeButton(negativeButtonText, new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          for (NegativeButtonDialogListener listener : getNegativeButtonDialogListeners()) {
            listener.onNegativeButtonClicked(mRequestCode);
          }
          dismiss();
        }
      });
    }

    final CharSequence neutralButtonText = getNeutralButtonText();
    if (!TextUtils.isEmpty(neutralButtonText)) {
      builder.setNeutralButton(neutralButtonText, new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          for (NeutralButtonDialogListener listener : getNeutralButtonDialogListeners()) {
            listener.onNeutralButtonClicked(mRequestCode);
          }
          dismiss();
        }
      });
    }

    return builder;
  }

  protected CharSequence getMessage() {
    return getArguments().getCharSequence(ARG_MESSAGE);
  }

  protected CharSequence getTitle() {
    return getArguments().getCharSequence(ARG_TITLE);
  }

  protected CharSequence getPositiveButtonText() {
    return getArguments().getCharSequence(ARG_POSITIVE_BUTTON);
  }

  protected CharSequence getNegativeButtonText() {
    return getArguments().getCharSequence(ARG_NEGATIVE_BUTTON);
  }

  protected CharSequence getNeutralButtonText() {
    return getArguments().getCharSequence(ARG_NEUTRAL_BUTTON);
  }

  /**
   * Get positive button dialog listeners.
   * There might be more than one listener.
   *
   * @return Dialog listeners
   * @since 2.1.0
   */
  protected List<PositiveButtonDialogListener> getPositiveButtonDialogListeners() {
    return getDialogListeners(PositiveButtonDialogListener.class);
  }

  /**
   * Get negative button dialog listeners.
   * There might be more than one listener.
   *
   * @return Dialog listeners
   * @since 2.1.0
   */
  protected List<NegativeButtonDialogListener> getNegativeButtonDialogListeners() {
    return getDialogListeners(NegativeButtonDialogListener.class);
  }

  /**
   * Get neutral button dialog listeners.
   * There might be more than one listener.
   *
   * @return Dialog listeners
   * @since 2.1.0
   */
  protected List<NeutralButtonDialogListener> getNeutralButtonDialogListeners() {
    return getDialogListeners(NeutralButtonDialogListener.class);
  }


  public static class SimpleDialogBuilder extends BaseDialogBuilder<SimpleDialogBuilder> {

    private CharSequence mTitle;
    private CharSequence mMessage;
    private CharSequence mPositiveButtonText;
    private CharSequence mNegativeButtonText;
    private CharSequence mNeutralButtonText;

    protected SimpleDialogBuilder(Context context, FragmentManager fragmentManager, Class<? extends SimpleDialogFragment> clazz) {
      super(context, fragmentManager, clazz);
    }

    @Override
    protected SimpleDialogBuilder self() {
      return this;
    }

    public SimpleDialogBuilder setTitle(int titleResourceId) {
      mTitle = mContext.getString(titleResourceId);
      return this;
    }


    public SimpleDialogBuilder setTitle(CharSequence title) {
      mTitle = title;
      return this;
    }

    public SimpleDialogBuilder setMessage(int messageResourceId) {
      mMessage = mContext.getText(messageResourceId);
      return this;
    }

    /**
     * Allow to set resource string with HTML formatting and bind %s,%i.
     * This is workaround for https://code.google.com/p/android/issues/detail?id=2923
     */
    public SimpleDialogBuilder setMessage(int resourceId, Object... formatArgs) {
      mMessage = Html.fromHtml(String.format(Html.toHtml(new SpannedString(mContext.getText(resourceId))), formatArgs));
      return this;
    }

    public SimpleDialogBuilder setMessage(CharSequence message) {
      mMessage = message;
      return this;
    }

    public SimpleDialogBuilder setPositiveButtonText(int textResourceId) {
      mPositiveButtonText = mContext.getString(textResourceId);
      return this;
    }

    public SimpleDialogBuilder setPositiveButtonText(CharSequence text) {
      mPositiveButtonText = text;
      return this;
    }

    public SimpleDialogBuilder setNegativeButtonText(int textResourceId) {
      mNegativeButtonText = mContext.getString(textResourceId);
      return this;
    }

    public SimpleDialogBuilder setNegativeButtonText(CharSequence text) {
      mNegativeButtonText = text;
      return this;
    }

    public SimpleDialogBuilder setNeutralButtonText(int textResourceId) {
      mNeutralButtonText = mContext.getString(textResourceId);
      return this;
    }

    public SimpleDialogBuilder setNeutralButtonText(CharSequence text) {
      mNeutralButtonText = text;
      return this;
    }

    @Override
    protected Bundle prepareArguments() {
      Bundle args = new Bundle();
      args.putCharSequence(SimpleDialogFragment.ARG_MESSAGE, mMessage);
      args.putCharSequence(SimpleDialogFragment.ARG_TITLE, mTitle);
      args.putCharSequence(SimpleDialogFragment.ARG_POSITIVE_BUTTON, mPositiveButtonText);
      args.putCharSequence(SimpleDialogFragment.ARG_NEGATIVE_BUTTON, mNegativeButtonText);
      args.putCharSequence(SimpleDialogFragment.ARG_NEUTRAL_BUTTON, mNeutralButtonText);

      return args;
    }
  }
}
