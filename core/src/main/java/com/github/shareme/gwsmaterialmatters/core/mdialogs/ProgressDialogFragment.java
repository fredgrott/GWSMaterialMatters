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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.shareme.gwsmaterialmatters.core.R;

/**
 * Simple progress dialog that shows indeterminate progress bar together with message and dialog title (optional).<br/>
 * <p>
 * To show the dialog, start with .
 * </p>
 * <p>
 * Dialog can be cancelable - to listen to cancellation, activity or target
 * fragment must implement {@link SimpleDialogCancelListener}
 * </p>

 * Created by fgrott on 6/21/2016.
 */
@SuppressWarnings("unused")
public class ProgressDialogFragment extends BaseDialogFragment {

  protected final static String ARG_MESSAGE = "message";
  protected final static String ARG_TITLE = "title";


  public static ProgressDialogBuilder createBuilder(Context context, FragmentManager fragmentManager) {
    return new ProgressDialogBuilder(context, fragmentManager);
  }

  @Override
  protected Builder build(Builder builder) {
    final LayoutInflater inflater = builder.getLayoutInflater();
    final View view = inflater.inflate(R.layout.sdl_progress, null, false);
    final TextView tvMessage = (TextView) view.findViewById(R.id.sdl_message);

    tvMessage.setText(getArguments().getCharSequence(ARG_MESSAGE));

    builder.setView(view);

    builder.setTitle(getArguments().getCharSequence(ARG_TITLE));

    return builder;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (getArguments() == null) {
      throw new IllegalArgumentException("use ProgressDialogBuilder to construct this dialog");
    }
  }


  public static class ProgressDialogBuilder extends BaseDialogBuilder<ProgressDialogBuilder> {

    private CharSequence mTitle;
    private CharSequence mMessage;

    protected ProgressDialogBuilder(Context context, FragmentManager fragmentManager) {
      super(context, fragmentManager, ProgressDialogFragment.class);
    }

    @Override
    protected ProgressDialogBuilder self() {
      return this;
    }

    public ProgressDialogBuilder setTitle(int titleResourceId) {
      mTitle = mContext.getString(titleResourceId);
      return this;
    }


    public ProgressDialogBuilder setTitle(CharSequence title) {
      mTitle = title;
      return this;
    }

    public ProgressDialogBuilder setMessage(int messageResourceId) {
      mMessage = mContext.getString(messageResourceId);
      return this;
    }

    public ProgressDialogBuilder setMessage(CharSequence message) {
      mMessage = message;
      return this;
    }

    @Override
    protected Bundle prepareArguments() {
      Bundle args = new Bundle();
      args.putCharSequence(SimpleDialogFragment.ARG_MESSAGE, mMessage);
      args.putCharSequence(SimpleDialogFragment.ARG_TITLE, mTitle);

      return args;
    }
  }
}
