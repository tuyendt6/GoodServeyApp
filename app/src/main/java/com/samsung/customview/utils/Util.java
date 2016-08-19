package com.samsung.customview.utils;

import android.content.Context;

/**
 * Created by shahroz on 1/8/2016.
 */
public class Util {
    public static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }
}
