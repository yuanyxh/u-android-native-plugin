package com.yuanyxh.camera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yuanyxh.camera.adapter.PhotoPickerAdapter;
import com.yuanyxh.camera.utils.FileTools;
import com.yuanyxh.camera.utils.SharedData;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class PhotoPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker);

        // 设置底部导航栏颜色
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        // 设置固定底部
        getWindow().setGravity(Gravity.BOTTOM);

        constraint = findViewById(R.id.photo_picker);

        initImages();
        initClose();
    }
    @Override
    protected void onResume() {
        super.onResume();

        // 全屏沉浸式, 隐藏状态栏导航栏
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay_in_place_medium, R.anim.slide_out_bottom_medium);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            PhotoPickerActivity.this.finishAfterTransition();
            overridePendingTransition(R.anim.stay_in_place_medium, R.anim.slide_out_bottom_medium);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private final AtomicReference<Float> startX = new AtomicReference<>((float) 0);
    private final AtomicReference<Float> startY = new AtomicReference<>((float) 0);
    private final AtomicReference<Float> moveX = new AtomicReference<>((float) 0);
    private final AtomicReference<Float> moveY = new AtomicReference<>((float) 0);
    private final AtomicLong currentMS = new AtomicLong();
    private final AtomicReference<Float> moveTime = new AtomicReference<>((float) 0);

    private ConstraintLayout constraint = null;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX.set(ev.getX());
                startY.set(ev.getY());

                currentMS.set(System.currentTimeMillis());

                break;

            case MotionEvent.ACTION_UP:
                moveTime.set((float) (System.currentTimeMillis() - currentMS.get()));

                moveX.set(Math.abs(ev.getX() - startX.get()));
                moveY.set(Math.abs(ev.getY() - startY.get()));

                if (
                        moveTime.get() < 300 &&
                                moveX.get() < 20 &&
                                moveY.get() < 20 &&
                                !isTouchInView(constraint, (int) ev.getRawX(), (int) ev.getRawY())
                ) {
                    moveX.set(0f);
                    moveY.set(0f);

                    PhotoPickerActivity.this.finishAfterTransition();
                    overridePendingTransition(R.anim.stay_in_place_medium, R.anim.slide_out_bottom_medium);

                    return true;
                }

                moveX.set(0f);
                moveY.set(0f);

                break;
            default:
        }

        return super.dispatchTouchEvent(ev);
    }

    public boolean isTouchInView(ViewGroup view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();

        return x >= left && x <= right && y >= top && y <= bottom;
    }

    public void initImages() {
        ArrayList<File> files = FileTools.getImagesOnPath(SharedData.emojiPath);

        RecyclerView recyclerView = findViewById(R.id.photo_container);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        recyclerView.setAdapter(new PhotoPickerAdapter(this, files));
    }

    public void initClose() {
        LinearLayout layout = findViewById(R.id.photo_close_wrap);

        View.OnClickListener listener = view -> {
            PhotoPickerActivity.this.finishAfterTransition();
            overridePendingTransition(R.anim.stay_in_place_medium, R.anim.slide_out_bottom_medium);
        };

        layout.setOnClickListener(listener);
    }
}