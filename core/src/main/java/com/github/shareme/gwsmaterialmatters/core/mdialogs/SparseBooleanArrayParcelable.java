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

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;

/**
 * Created by fgrott on 6/21/2016.
 */
@SuppressWarnings("unused")
public class SparseBooleanArrayParcelable extends SparseBooleanArray implements Parcelable {
  public static Parcelable.Creator<SparseBooleanArrayParcelable> CREATOR = new Parcelable.Creator<SparseBooleanArrayParcelable>() {
    @Override
    public SparseBooleanArrayParcelable createFromParcel(Parcel source) {
      SparseBooleanArrayParcelable read = new SparseBooleanArrayParcelable();
      int size = source.readInt();

      int[] keys = new int[size];
      boolean[] values = new boolean[size];

      source.readIntArray(keys);
      source.readBooleanArray(values);

      for (int i = 0; i < size; i++) {
        read.put(keys[i], values[i]);
      }

      return read;
    }

    @Override
    public SparseBooleanArrayParcelable[] newArray(int size) {
      return new SparseBooleanArrayParcelable[size];
    }
  };

  public SparseBooleanArrayParcelable() {

  }

  public SparseBooleanArrayParcelable(SparseBooleanArray sparseBooleanArray) {
    for (int i = 0; i < sparseBooleanArray.size(); i++) {
      this.put(sparseBooleanArray.keyAt(i), sparseBooleanArray.valueAt(i));
    }
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    int[] keys = new int[size()];
    boolean[] values = new boolean[size()];

    for (int i = 0; i < size(); i++) {
      keys[i] = keyAt(i);
      values[i] = valueAt(i);
    }

    dest.writeInt(size());
    dest.writeIntArray(keys);
    dest.writeBooleanArray(values);
  }
}