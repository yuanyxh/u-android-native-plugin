package com.yuanyxh.camera.utils;

import android.app.Activity;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsAnimationCompat;
import androidx.core.view.WindowInsetsCompat;


import java.util.List;

public class Keyboard {
    private final Activity context;

    private boolean visible = false;

    public Keyboard(Activity _context) {
        context = _context;
    }

    private float keyboardHeight = 0f;

    public void onKeyboardHeightChanged(OnKeyboardHeightChangeCallback callback) {
        Window window = context.getWindow();
        View view = window.getDecorView();

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            visible = getImeVisible(insets);
            keyboardHeight = getInsetBottom(insets);

            return insets;
        });

        ViewCompat.setWindowInsetsAnimationCallback(view, new WindowInsetsAnimationCompat.Callback(WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_STOP) {

            @NonNull
            @Override
            public WindowInsetsCompat onProgress(@NonNull WindowInsetsCompat insets, @NonNull List<WindowInsetsAnimationCompat> runningAnimations) {
                WindowInsetsAnimationCompat imeAnimation = null;
                for (WindowInsetsAnimationCompat animation : runningAnimations) {
                    if ((animation.getTypeMask() & WindowInsetsCompat.Type.ime()) != 0) {
                        imeAnimation = animation;
                        break;
                    }
                }

                if (imeAnimation == null) return insets;

                // float duration = imeAnimation.getInterpolatedFraction();

                callback.onChange(
                        visible,
                        keyboardHeight,
                        getInsetBottom(insets)
                );

                return insets;
            }
        });
    }

    private boolean getImeVisible(WindowInsetsCompat insets) {
        return insets.isVisible(WindowInsetsCompat.Type.ime());
    }

    private float getInsetBottom(WindowInsetsCompat insets) {
        return insets.getInsets(WindowInsetsCompat.Type.ime()).bottom;
    }

    public abstract static class OnKeyboardHeightChangeCallback {
        public abstract void onChange(boolean visible, float keyboardHeight, float currentHeight);
    }
}
