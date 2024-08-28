package com.yuanyxh.camera.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.yuanyxh.camera.R;

public class AnimationTools {
    private static Animation anim_scale;

    public static void scale(Activity context, View view) {
        if (anim_scale == null) {
            anim_scale = AnimationUtils.loadAnimation(context, R.anim.capture_scale);
        }

        Animation.AnimationListener listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                anim_scale.setAnimationListener(null);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };

        anim_scale.setAnimationListener(listener);

        view.startAnimation(anim_scale);
    }

    public static void scale(Activity context, View view, long duration) {
        if (anim_scale == null) {
            anim_scale = AnimationUtils.loadAnimation(context, R.anim.capture_scale);
        }

        anim_scale.setDuration(duration);

        Animation.AnimationListener listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                anim_scale.setAnimationListener(null);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };

        anim_scale.setAnimationListener(listener);

        view.startAnimation(anim_scale);
    }

    public static void scale(Activity context, View view, AnimationEndListener endListener) {
        if (anim_scale == null) {
            anim_scale = AnimationUtils.loadAnimation(context, R.anim.capture_scale);
        }

        Animation.AnimationListener listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                endListener.end();
                view.clearAnimation();
                anim_scale.setAnimationListener(null);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };

        anim_scale.setAnimationListener(listener);

        view.startAnimation(anim_scale);
    }


    public static void scale(Activity context, View view, long duration, AnimationEndListener endListener) {
        if (anim_scale == null) {
            anim_scale = AnimationUtils.loadAnimation(context, R.anim.capture_scale);
        }

        anim_scale.setDuration(duration);

        Animation.AnimationListener listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                endListener.end();
                view.clearAnimation();
                anim_scale.setAnimationListener(null);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };

        anim_scale.setAnimationListener(listener);

        view.startAnimation(anim_scale);
    }

    public static void fadeIn(View view) {
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);

        view.animate().alpha(1f).setDuration(200).setListener(null);
    }

    public static void fadeOut(View view) {
        view.setAlpha(1f);

        view.animate().alpha(0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        });
    }

    public interface AnimationEndListener {
        void end();
    }
}
