package com.yuanyxh.notify;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.core.app.NotificationManagerCompat;

import com.yuanyxh.notify.utils.NotificationFactory;
import com.yuanyxh.notify.utils.NotificationToast;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.common.UniModule;

public class NotifyModule extends UniModule {

    @UniJSMethod(uiThread = false)
    public void transmitNotify() {
        show(mUniSDKInstance.getContext());
    }

    private void show(Context context) {
        notice(context);
    }

    @SuppressLint("InflateParams")
    private void notice(Context context) {
        int notificationId = 0x1001;

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationFactory factory = new NotificationFactory(context.getApplicationContext(), manager);

        Notification notification = factory.createNotification("id1", "I am notification title1",
                "I am notification contentxxxxxxxxxxx1");

        factory.clear();

        if (areNotificationsEnabled(context)) {
            manager.notify(notificationId, notification);
        } else {
            if (context instanceof Activity) {
                View view = ((Activity) context).getLayoutInflater().inflate(R.layout.layout_notify, null);
                NotificationToast
                        .show((Activity) context, notificationId, view);
            }

        }
    }

    private boolean areNotificationsEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // 在 Android 8.0 及以上版本
            return manager.areNotificationsEnabled();
        } else {
            // 在 Android 7.0 及以下版本
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        }
    }
}
