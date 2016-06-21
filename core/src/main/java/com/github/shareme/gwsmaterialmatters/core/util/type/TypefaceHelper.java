package com.github.shareme.gwsmaterialmatters.core.util.type;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.SimpleArrayMap;

/**
 * Each call to Typeface.createFromAsset will load a new instance of the typeface into memory,
 * and this memory is not consistently get garbage collected
 * http://code.google.com/p/android/issues/detail?id=9904
 * (It states released but even on Lollipop you can see the typefaces accumulate even after
 * multiple GC passes)
 *
 * You can detect this by running:
 * adb shell dumpsys meminfo com.your.packagenage
 *
 * You will see output like:
 *
 * Asset Allocations
 * zip:/data/app/com.your.packagenage-1.apk:/assets/Roboto-Medium.ttf: 125K
 * zip:/data/app/com.your.packagenage-1.apk:/assets/Roboto-Medium.ttf: 125K
 * zip:/data/app/com.your.packagenage-1.apk:/assets/Roboto-Medium.ttf: 125K
 * zip:/data/app/com.your.packagenage-1.apk:/assets/Roboto-Regular.ttf: 123K
 * zip:/data/app/com.your.packagenage-1.apk:/assets/Roboto-Medium.ttf: 125K
 *
 * fonts per API/OS version:
 * 16  Roboto RobotoCondensed Roboto-Light
 * 17  Roboto RobotoCondensed Roboto-Light Roboto-Thin
 * 18  Roboto RobotoCondensed Roboto-Light Roboto-Thin
 * 19  Roboto RobotoCondensed Roboto-Light Roboto-Thin
 * 20  Roboto RobotoCondensed RobotoCondensed-Light Roboto-Light Roboto-Thin
 * 21  Roboto RobotoCondensed RobotoCondensed-Light Roboto-Light Roboto-Thin Roboto-Medium Roboto-Black
 * 22  Roboto RobotoCondensed RobotoCondensed-Light Roboto-Light Roboto-Thin Roboto-Medium Roboto-Black
 * 23  Roboto RobotoCondensed RobotoCondensed-Light Roboto-Light Roboto-Thin Roboto-Medium Roboto-Black
 * 24  Roboto RobotoCondensed RobotoCondensed-Light Roboto-Light Roboto-Thin Roboto-Medium Roboto-Black
 *
 * and we have
 *   NotoSerif DroidSans DroidSansMono CutiveMono ComingSoon DancingScript CarroisGothicSC-Regular
 *
 * So our Font minimum stack for support of Material Design across api 16 to 24 has to be:
 *    Roboto-Thin
 *    Roboto-Medium
 *    Roboto-Black
 *    RobotoCondensed-Light
 *    NotoSerif
 *    CutiveMono
 *    ComingSoon
 *    DancingScript
 *    CarroisGothicSC-Regular
 *
 * Created by fgrott on 6/20/2016.
 */
@SuppressWarnings("unused")
public class TypefaceHelper {

  private static final SimpleArrayMap<String, Typeface> cache = new SimpleArrayMap<>();

  public static Typeface get(Context c, String name) {
    synchronized (cache) {
      if (!cache.containsKey(name)) {
        try {
          Typeface t = Typeface.createFromAsset(
                  c.getAssets(), String.format("fonts/%s", name));
          cache.put(name, t);
          return t;
        } catch (RuntimeException e) {
          return null;
        }
      }
      return cache.get(name);
    }
  }
}

