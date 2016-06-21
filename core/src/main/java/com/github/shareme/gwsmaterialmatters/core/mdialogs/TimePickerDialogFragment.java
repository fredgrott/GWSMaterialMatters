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
import android.widget.TimePicker;

import com.github.shareme.gwsmaterialmatters.core.R;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Dialog with a time picker.
 * <p/>
 * Implement {@link DateDialogListener}
 * and/or {@link SimpleDialogCancelListener} to handle events.
 * Created by fgrott on 6/21/2016.
 */
@SuppressWarnings("unused")
public class TimePickerDialogFragment extends DatePickerDialogFragment {

  TimePicker mTimePicker;
  Calendar mCalendar;


  public static SimpleDialogBuilder createBuilder(Context context, FragmentManager fragmentManager) {
    return new SimpleDialogBuilder(context, fragmentManager, TimePickerDialogFragment.class);
  }

  @Override
  protected BaseDialogFragment.Builder build(BaseDialogFragment.Builder builder) {
    builder = super.build(builder);
    mTimePicker = (TimePicker) builder.getLayoutInflater().inflate(R.layout.sdl_timepicker, null);
    mTimePicker.setIs24HourView(getArguments().getBoolean(ARG_24H));
    builder.setView(mTimePicker);

    TimeZone zone = TimeZone.getTimeZone(getArguments().getString(ARG_ZONE));
    mCalendar = Calendar.getInstance(zone);
    mCalendar.setTimeInMillis(getArguments().getLong(ARG_DATE, System.currentTimeMillis()));

    mTimePicker.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
    mTimePicker.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
    return builder;
  }

  public Date getDate() {
    mCalendar.set(Calendar.HOUR_OF_DAY, mTimePicker.getCurrentHour());
    mCalendar.set(Calendar.MINUTE, mTimePicker.getCurrentMinute());
    return mCalendar.getTime();
  }
}
