package com.yuanyxh.notify.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.provider.MediaStore;

import com.yuanyxh.notify.R;

public class NotificationFactory {
    // 图标
    private int mAppIcon = -1;

    // 图片
    private Bitmap mAppBitmap;

    private Context mContext;

    private NotificationManager mNotificationManager;

    public NotificationFactory(Context context, NotificationManager notificationManager) {
        mContext = context;
        mNotificationManager = notificationManager;
    }

    public Notification createNotification(String id, String title, String content) {
        long[] vibrate = {0, 40, 20, 40, 20, 40, 20, 40, 20, 40, 20, 40};

        // 通知点击事件
        // Intent intentClick = new Intent(mContext, TargetActivity.class);
        // intentClick.setAction(TargetActivity.ACTION_CLICK);
        // PendingIntent pendingIntentClick = PendingIntent
        //         .getActivity(mContext, id.hashCode(), intentClick,
        //                 PendingIntent.FLAG_ONE_SHOT);

        // Intent intentDelete = new Intent(mContext, PushResultActivity.class);
        // intentDelete.setAction(PushResultActivity.ACTION_CANCEL);
        // PendingIntent pendingIntentDelete = PendingIntent
        //         .getActivity(mContext, id.hashCode(), intentDelete,
        //                 PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder notificationBuilder = new Notification
                .Builder(mContext)
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.avatar)
                // .setLargeIcon(getAppBitmap(mContext))
                .setVibrate(vibrate)
                .setTicker(title)
                // .setPriority(Notification.PRIORITY_MAX)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSound(MediaStore.Audio.Media.INTERNAL_CONTENT_URI)
                // .setContentIntent(pendingIntentClick)
                // .setDeleteIntent(pendingIntentDelete)
                .setAutoCancel(true);

        notificationBuilder.setShowWhen(true);
        notificationBuilder.setCategory(Notification.CATEGORY_STATUS);

        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel_id_push",
                    mContext.getPackageName(),
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            mNotificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(channel.getId());
            notificationBuilder.setSmallIcon(Icon.createWithResource(mContext, R.drawable.avatar));
            // notificationBuilder.setLargeIcon(Icon.createWithResource(mContext, getAppIcon(mContext)));
        }
        return notificationBuilder.build();
    }

    private int getAppIcon(Context context) {
        if (mAppIcon > 0) {
            return mAppIcon;
        }
        try {
            mAppIcon = context.getApplicationInfo().icon;
            return mAppIcon;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mAppIcon;
    }

    private Bitmap getAppBitmap(Context context) {
        if (mAppBitmap != null) {
            return mAppBitmap;
        }

        int appIcon = getAppIcon(mContext);
        if (appIcon < 0) {
            appIcon = -1;
        }

        mAppBitmap = BitmapFactory.decodeResource(context.getResources(), appIcon);

        return mAppBitmap;
    }

    public void clear() {
        mAppBitmap = null;
        mAppIcon = -1;
        mContext = null;
        mNotificationManager = null;
    }

}
