package com.yuanyxh.camera.utils;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.widget.LinearLayout;

public class Divider {
    public static void insetDividerToLinearLayout(Activity context, int layoutId, int width) {
        LinearLayout linearLayout = context.findViewById(layoutId);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setSize(width, 1);

        linearLayout.setDividerDrawable(drawable);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
    }
}
