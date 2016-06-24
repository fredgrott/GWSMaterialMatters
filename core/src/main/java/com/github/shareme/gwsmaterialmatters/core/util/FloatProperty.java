package com.github.shareme.gwsmaterialmatters.core.util;

import android.annotation.SuppressLint;
import android.util.Property;

/**
 * Dummy class. Permits to extend same hidden class from android framework.
 * Actually in runtime will be used class from android framework and ObjectAnimator
 * optimizations for FloatProperty will be applied.
 * Created by fgrott on 6/23/2016.
 */
@SuppressWarnings("unused")
public abstract class FloatProperty<T> extends Property<T, Float> {

  public FloatProperty(String name) {
    super(Float.class, name);
  }

  public abstract void setValue(T object, float value);

  @SuppressLint("NewApi")
  @Override
  final public void set(T object, Float value) {
    setValue(object, value);
  }

}
