package com.yuanyxh.notify.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yuanyxh.notify.R;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

public class NotificationToast {

    public static void show(Activity context, int notificationId, View contentView) {
        showByToastToken(context, notificationId, contentView);
    }


    @SuppressLint("ClickableViewAccessibility")
    private static void showByToastToken(Activity context, final int notificationId, View root) {
        try {
            final Toast toast = Toast.makeText(root.getContext(), "", Toast.LENGTH_SHORT);

            FrameLayout layout = (FrameLayout) root;
            RelativeLayout content_view = (RelativeLayout) layout.getChildAt(0);
            if (content_view != null) {
                ViewGroup.LayoutParams layoutParams = content_view.getLayoutParams();
                layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
                content_view.setLayoutParams(layoutParams);

                AtomicReference<Float> x = new AtomicReference<>(0f);
                View.OnTouchListener touchListener = (view, motionEvent) -> {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            x.set(motionEvent.getRawX());
                            break;
                        case MotionEvent.ACTION_MOVE:
                            content_view
                                    .animate()
                                    .setDuration(0)
                                    .translationX(motionEvent.getRawX() - x.get());
                            break;
                        case MotionEvent.ACTION_UP:
                            x.set(0f);

                            float translateX = content_view.getTranslationX();
                            if (Math.abs(translateX) < content_view.getWidth() / 5f) {
                                content_view
                                        .animate()
                                        .setDuration(100)
                                        .translationX(0f);
                            } else {
                                content_view
                                        .animate()
                                        .setDuration(200)
                                        .translationX(translateX * 10f);
                            }
                            break;
                        default:
                    }

                    return true;
                };

                content_view.setOnTouchListener(touchListener);

                try {
                    Class<?> clazz = toast.getClass();
                    @SuppressLint("DiscouragedPrivateApi") Method getWindowParams = clazz.getDeclaredMethod("getWindowParams");
                    WindowManager.LayoutParams windowLayoutParams = (WindowManager.LayoutParams) getWindowParams.invoke(toast);

                    if (windowLayoutParams != null) {
                        windowLayoutParams.windowAnimations = R.style.Toast;
                        windowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

                        toast.setView(root);
                        toast.setGravity(Gravity.TOP|Gravity.FILL_HORIZONTAL, 0, 0);

                        toast.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void clickContentView(Context context, int notificationId) {
        // Intent intentClick = new Intent(context, PushResultActivity.class);
        // intentClick.setAction(PushResultActivity.ACTION_CLICK);
        // context.startActivity(intentClick);
        // remove notification
        // NotificationManager manager = (NotificationManager) context
        //         .getSystemService(Context.NOTIFICATION_SERVICE);
        // manager.cancel(notificationId);
    }

    private static void hideToast(Object mTnObj) {
        try {
            Method handleHide = mTnObj.getClass()
                    .getDeclaredMethod("handleHide", (Class<?>[]) null);
            handleHide.setAccessible(true);
            handleHide.invoke(mTnObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
