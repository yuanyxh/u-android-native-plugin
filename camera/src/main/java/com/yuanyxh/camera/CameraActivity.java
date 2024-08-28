package com.yuanyxh.camera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.gesture.Gesture;
import com.otaliastudios.cameraview.gesture.GestureAction;
import com.otaliastudios.cameraview.markers.DefaultAutoFocusMarker;
import com.yuanyxh.camera.utils.AnimationTools;
import com.yuanyxh.camera.utils.BitmapBinder;
import com.yuanyxh.camera.utils.NavigationBar;
import com.yuanyxh.camera.utils.SharedData;

public class CameraActivity extends AppCompatActivity {
    private CameraView camera = null;

    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // 设置底部导航栏颜色
        getWindow().setNavigationBarColor(Color.TRANSPARENT);

        initCamera();
        initCapture();
        initClose();
        initToggle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera.open();

        // 适配底部虚拟导航栏
        if (NavigationBar.isNavigationBarShow(this)) {
            findViewById(R.id.operator).setPadding(0, 0, 0, NavigationBar.getNavigationBarHeight(this));
        }

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
    protected void onPause() {
        camera.close();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (camera != null) camera.destroy();

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(0, R.anim.slide_out_bottom);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            CameraActivity.this.finishAfterTransition();
            overridePendingTransition(0, R.anim.slide_out_bottom);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == SharedData.DRAW_PICTURE_RESULT && data != null) {
            Bundle bundle = data.getBundleExtra("result_bundle");
            if (bundle != null) {
                BitmapBinder binder = (BitmapBinder) bundle.getBinder("result");
                if (binder != null) {
                    Bitmap bitmap = binder.getBitmap();

                    if (bitmap != null) {
                        Intent intent = new Intent();
                        Bundle _bundle = new Bundle();
                        _bundle.putBinder("final_result", new BitmapBinder(bitmap));
                        intent.putExtra("final_result_bundle", _bundle);
                        setResult(SharedData.FINAL_PICTURE_RESULT, intent);
                        finishAfterTransition();
                        overridePendingTransition(0, R.anim.slide_out_bottom);
                    }
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initCamera() {
        camera = findViewById(R.id.camera);

        // 获取屏幕刷新率
        // Display display = getWindowManager().getDefaultDisplay();
        // float refreshRate = display.getRefreshRate();
        // float fRefreshRate = (float) Math.floor(refreshRate);

        // 设置相机预览帧率
        // camera.setPreviewFrameRate(fRefreshRate);

        camera.setLifecycleOwner(this);

        // camera.setPreviewFrameRateExact(true);

        // 捏合缩放
        camera.mapGesture(Gesture.PINCH, GestureAction.ZOOM);
        // 点按聚焦
        camera.mapGesture(Gesture.TAP, GestureAction.AUTO_FOCUS);
        // 垂直滑动曝光
        // camera.mapGesture(Gesture.SCROLL_VERTICAL, GestureAction.EXPOSURE_CORRECTION);

        // 曝光 marker
        camera.setAutoFocusMarker(new DefaultAutoFocusMarker());

        BitmapCallback bitmapCallback = bitmap -> {
            Intent intent = new Intent(CameraActivity.this, PhotoEditorActivity.class);

            BitmapBinder binder = new BitmapBinder(bitmap);
            Bundle bundle = new Bundle();
            bundle.putBinder("picture", binder);
            intent.putExtra("picture_bundle", bundle);

            startActivityForResult(intent, SharedData.DRAW_PICTURE_RESULT);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        };

        CameraListener cameraListener = new CameraListener() {
            public void onCameraError(@NonNull CameraException error) {
            }

            public void onPictureTaken(@NonNull PictureResult result) {
                isRunning = false;
                result.toBitmap(bitmapCallback);
            }

            public void onOrientationChanged(int orientation) {
            }
        };

        camera.addCameraListener(cameraListener);
    }

    private void initCapture() {
        final ImageButton capture = findViewById(R.id.capture);

        View.OnClickListener captureOnClickListener = view -> {
            if (isRunning) return;

            isRunning = true;

            AnimationTools.scale(this, capture);

            camera.takePictureSnapshot();
        };

        capture.setOnClickListener(captureOnClickListener);
    }

    private void initClose() {
        final ImageButton camera_close = findViewById(R.id.camera_close);

        View.OnClickListener closeOnClickListener = view -> {
            if (isRunning) return;

            isRunning = true;

            AnimationTools.scale(this, camera_close, 50);

            CameraActivity.this.finishAfterTransition();

            overridePendingTransition(0, R.anim.slide_out_bottom);
        };

        camera_close.setOnClickListener(closeOnClickListener);
    }

    private void initToggle() {
        final ImageButton camera_toggle = findViewById(R.id.camera_toggle);

        AnimationTools.AnimationEndListener endListener = () -> isRunning = false;

        camera_toggle.setOnClickListener(view -> {
            if (isRunning) return;

            isRunning = true;

            if (camera.getFacing() == Facing.BACK) {
                camera.setFacing(Facing.FRONT);
            } else {
                camera.setFacing(Facing.BACK);
            }

            AnimationTools.scale(this, camera_toggle, endListener);
        });
    }
}