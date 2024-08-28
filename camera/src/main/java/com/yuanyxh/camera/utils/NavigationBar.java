package com.yuanyxh.camera.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;

public class NavigationBar {

    public static int getNavigationBarHeight(Activity context) {
        Resources resources = context.getResources();
        @SuppressLint({"DiscouragedApi", "InternalInsetResource"}) int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    public static boolean isNavigationBarShow(Activity context) {
        if (getNavigationBarHeight(context) == 0) {
            return false;
        }

        if (RomUtils.isHuawei()) {
            return !isHuaWeiHideNav(context);
        } else if (RomUtils.isXiaomi()) {
            return !isMiuiFullScreen(context);
        } else if (RomUtils.isVivo()) {
            return !isVivoFullScreen(context);
        } else {
            return isShowNavigationBar(context);
        }
    }

    private static boolean isHuaWeiHideNav(Activity context) {
        return Settings.Global.getInt(context.getContentResolver(), "navigationbar_is_min", 0) != 0;
    }

    private static boolean isMiuiFullScreen(Activity context) {
        return Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
    }

    private static boolean isVivoFullScreen(Activity context) {
        return Settings.Secure.getInt(context.getContentResolver(), "navigation_gesture_on", 0) != 0;
    }


    private static boolean isShowNavigationBar(Activity context) {
        Display defaultDisplay = context.getWindowManager().getDefaultDisplay();
        // 获取屏幕高度
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(outMetrics);
        int heightPixels = outMetrics.heightPixels;
        // 宽度
        int widthPixels = outMetrics.widthPixels;


        // 获取内容高度
        DisplayMetrics outMetrics2 = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics2);
        int heightPixels2 = outMetrics2.heightPixels;
        // 宽度
        int widthPixels2 = outMetrics2.widthPixels;

        return heightPixels - heightPixels2 > 0 || widthPixels - widthPixels2 > 0;
    }
}
