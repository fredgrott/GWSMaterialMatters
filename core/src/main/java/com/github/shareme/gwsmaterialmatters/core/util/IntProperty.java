package com.github.shareme.gwsmaterialmatters.core.util;

import android.annotation.SuppressLint;
import android.util.Property;

/**
 * Dummy class. Permits to extend same hidden class from android framework.
 * Actually in runtime will be used class from android framework and ObjectAnimator
 * optimizations for IntProperty will be applied.
 * Created by fgrott on 6/23/2016.
 */
@SuppressWarnings("unused")
public abstract class IntProperty<T> extends Property<T, Integer> {

  public IntProperty(String name) {
    super(Integer.class, name);
  }

  public abstract void setValue(T object, int value);

  @SuppressLint("NewApi")
  @Override
  final public void set(T object, Integer value) {
    setValue(object, value);
  }

}
