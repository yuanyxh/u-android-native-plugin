package com.yuanyxh.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.yuanyxh.camera.utils.AnimationTools;
import com.yuanyxh.camera.utils.BitmapBinder;
import com.yuanyxh.camera.utils.Divider;
import com.yuanyxh.camera.utils.GraffitiColor;
import com.yuanyxh.camera.utils.Keyboard;
import com.yuanyxh.camera.utils.NavigationBar;
import com.yuanyxh.camera.utils.SharedData;
import com.yuanyxh.camera.view.Concentric;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.TextStyleBuilder;
import ja.burhanrashid52.photoeditor.ViewType;
import ja.burhanrashid52.photoeditor.shape.ShapeBuilder;

public class PhotoEditorActivity extends AppCompatActivity {
    private PhotoEditorView editorView = null;

    private PhotoEditor editor = null;

    private View rootView = null;

    private File cut_source = null;

    private File desCut_source = null;

    private final TextStyleBuilder textStyleBuilder = new TextStyleBuilder();

    private int visibilityStore = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_editor);

        // 设置底部导航栏颜色
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        // 设置顶部状态栏颜色
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        // 设置绘图编辑器间隔
        Divider.insetDividerToLinearLayout(this, R.id.painting, 30);

        initEditor();
        initMaskView();
        initBack();
        initGraffiti();
        initGraffitiColor();
        initAddEmoji();
        initAddText();
        initCrop();
        initUndoRedo();
        initComplete();

        initTextEvent();
        initEdit();
        initKeyboardChange();
        initTextGraffitiColor();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 适配底部虚拟导航栏
        if (NavigationBar.isNavigationBarShow(this)) {
            findViewById(R.id.operator).setPadding(0, 0, 0, NavigationBar.getNavigationBarHeight(this));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 必须开启无边框显示才能使 setOnApplyWindowInsetsListener 与 setWindowInsetsAnimationCallback 生效
            getWindow().setDecorFitsSystemWindows(false);

            // 全屏沉浸式, 隐藏状态栏导航栏
            // WindowInsetsController controller = getWindow().getInsetsController();
            //    if (controller != null) {
            //        controller.hide(WindowInsetsCompat.Type.systemBars());
            //        controller.hide(WindowInsetsCompat.Type.statusBars());
            //         controller.hide(WindowInsetsCompat.Type.navigationBars());
            //    }
        } else {
            // 必须开启无边框显示才能使 setOnApplyWindowInsetsListener 与 setWindowInsetsAnimationCallback 生效
            WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

            // 全屏沉浸式, 隐藏状态栏导航栏
            // WindowInsetsControllerCompat insetsControllerCompat = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
            // if (insetsControllerCompat != null) {
            //     insetsControllerCompat.hide(WindowInsetsCompat.Type.systemBars());
            //     insetsControllerCompat.hide(WindowInsetsCompat.Type.statusBars());
            //     insetsControllerCompat.hide(WindowInsetsCompat.Type.navigationBars());
            // }
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

        // 在 onResume 时, 判断是否从 PhotoPickerActivity 中返回, 以决定 back 按钮的展示隐藏
        RelativeLayout back_wrap = findViewById(R.id.back_wrap);
        int visibility = back_wrap.getVisibility();
        if (visibilityStore != -1 && visibilityStore != visibility) {
            if (visibilityStore == View.VISIBLE) {
                AnimationTools.fadeIn(back_wrap);
            } else {
                AnimationTools.fadeOut(back_wrap);
            }
        }
    }

    @Override
    protected void onPause() {
        RelativeLayout back_wrap = findViewById(R.id.back_wrap);
        // 在 onPause 中, 缓存 back 的展示状态
        visibilityStore = back_wrap.getVisibility();

        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            PhotoEditorActivity.this.finishAfterTransition();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 从 PhotoPickerActivity 返回并携带 Bitmap 时, 添加图片至编辑器
        if (resultCode == SharedData.ADD_EMOJI_RESULT && data != null) {
            Bundle bundle = data.getBundleExtra("emoji_bundle");

            if (bundle != null) {
                BitmapBinder binder = (BitmapBinder) bundle.getBinder("emoji");
                if (binder != null) {
                    editor.addImage(binder.getBitmap());
                }
            }
        }

        // 处理从 UCropActivity 返回的数据
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && data != null) {
            Uri resultUri = UCrop.getOutput(data);

            if (resultUri != null) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(resultUri));
                    editorView.getSource().setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        // else if (resultCode == UCrop.RESULT_ERROR && data != null) {
        //     Throwable cropError = UCrop.getError(data);
        // }

        if (cut_source != null && desCut_source != null) {
            cut_source.delete();
            desCut_source.delete();

            cut_source = null;
            desCut_source = null;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // 初始化 photo_editor
    private void initEditor() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("picture_bundle");

        if (bundle != null) {
            editorView = findViewById(R.id.photoEditorView);
            EditText editText = findViewById(R.id.editor_edit);

            BitmapBinder binder = (BitmapBinder) bundle.getBinder("picture");
            if (binder != null && binder.getBitmap() != null) {
                RelativeLayout mask_view = findViewById(R.id.mask_view);
                RelativeLayout text_editor_mask =  findViewById(R.id.text_editor_mask);

                editorView.getSource().setImageBitmap(binder.getBitmap());
                editor = new PhotoEditor.Builder(this, editorView)
                        .setPinchTextScalable(true)
                        .build();

                editor.setOnPhotoEditorListener(new OnPhotoEditorListener() {
                    @Override
                    public void onEditTextChangeListener(View _rootView, String text, int colorCode) {
                        rootView = _rootView;
                        editText.setTextColor(colorCode);
                        editText.setText(text);

                        AnimationTools.fadeIn(text_editor_mask);
                        AnimationTools.fadeOut(mask_view);

                        editTextFocus(editText);
                    }

                    @Override
                    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {}
                    @Override
                    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {}
                    @Override
                    public void onStartViewChangeListener(ViewType viewType) {}
                    @Override
                    public void onStopViewChangeListener(ViewType viewType) {}
                    @Override
                    public void onTouchSourceImage(MotionEvent event) {}
                });
            }
        }
    }

    // 初始化遮罩相关事件
    @SuppressLint("ClickableViewAccessibility")
    private void initMaskView() {
        RelativeLayout mask = findViewById(R.id.mask_view);
        RelativeLayout back_wrap = findViewById(R.id.back_wrap);
        RelativeLayout operator = findViewById(R.id.operator);

        AtomicReference<Float> startX = new AtomicReference<>((float) 0);
        AtomicReference<Float> startY = new AtomicReference<>((float) 0);
        AtomicReference<Float> moveX = new AtomicReference<>((float) 0);
        AtomicReference<Float> moveY = new AtomicReference<>((float) 0);
        AtomicLong currentMS = new AtomicLong();
        AtomicReference<Float> moveTime = new AtomicReference<>((float) 0);

        @SuppressLint("ClickableViewAccessibility") View.OnTouchListener listener = (view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX.set(motionEvent.getX());
                    startY.set(motionEvent.getY());

                    currentMS.set(System.currentTimeMillis());

                    break;

                case MotionEvent.ACTION_MOVE:
                    moveX.set(moveX.get() + Math.abs(motionEvent.getRawX() - startX.get()));
                    moveY.set(moveY.get() + Math.abs(motionEvent.getRawY() - startY.get()));

                    break;

                case MotionEvent.ACTION_UP:
                    moveTime.set((float) (System.currentTimeMillis() - currentMS.get()));

                    if (moveTime.get() < 300 && moveX.get() < 20 && moveY.get() < 20) {
                        int visibility = back_wrap.getVisibility() & operator.getVisibility();

                        if (visibility == View.VISIBLE) {
                            AnimationTools.fadeOut(back_wrap);
                            AnimationTools.fadeOut((operator));
                        } else {
                            AnimationTools.fadeIn(back_wrap);
                            AnimationTools.fadeIn((operator));
                        }

                        moveX.set(0f);
                        moveY.set(0f);
                    }

                    moveX.set(0f);
                    moveY.set(0f);

                    break;
                default:
                    break;
            }

            editorView.dispatchTouchEvent(motionEvent);

            return false;
        };

        mask.setOnTouchListener(listener);
    }

    // 初始化 back 事件
    private void initBack() {
        ImageButton back = findViewById(R.id.editor_back);

        View.OnClickListener listener = view -> {
            AnimationTools.scale(this, back, 50);

            this.finishAfterTransition();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        };

        back.setOnClickListener(listener);
    }

    // 初始化画笔事件
    private void initGraffiti() {
        ImageButton graffiti = findViewById(R.id.editor_graffiti);
        LinearLayout tools_wrap = findViewById(R.id.tools_wrap);

        View.OnClickListener listener = view -> {
            AnimationTools.scale(this, graffiti, 50);
            editor.setBrushDrawingMode(!editor.getBrushDrawableMode());

            if (editor.getBrushDrawableMode()) {
                AnimationTools.fadeIn(tools_wrap);
            } else {
                AnimationTools.fadeOut(tools_wrap);
            }

            Drawable drawable = graffiti.getDrawable();

            drawable.setTint(getResources().getColor(
                        editor.getBrushDrawableMode() ?
                                R.color.primary :
                                R.color.white
                    )
            );
        };

        graffiti.setOnClickListener(listener);
    }

    // 初始化画笔颜色
    public void initGraffitiColor() {
        LinearLayout layout = findViewById(R.id.graffiti_color);

        ArrayList<Integer> list = GraffitiColor.getColors();

        ArrayList<Concentric> concentrates = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
            Concentric concentric = new Concentric(this);

            concentric.setLayoutParams(params);

            concentric.setRadius(25);
            concentric.setColor(list.get(i));

            View.OnClickListener listener = view -> {
                for (int j = 0; j < concentrates.size(); j++) {
                    concentrates.get(j).setRadius(25);
                    concentrates.get(j).setStrokeWidth(6);
                }
                concentric.setRadius(30);
                concentric.setStrokeWidth(11);

                setEditorShapeColor(concentric.getColor());
            };

            concentric.setOnClickListener(listener);

            if (concentric.getColor() == GraffitiColor.RED) {
                concentric.setRadius(30);
                concentric.setStrokeWidth(11);
                setEditorShapeColor(concentric.getColor());
            }

            layout.addView(concentric);
            concentrates.add(concentric);
        }
    }

    // 初始化图片添加事件
    public void initAddEmoji() {
        RelativeLayout back_wrap = findViewById(R.id.back_wrap);
        ImageButton editor_emoji = findViewById(R.id.editor_emoji);

        View.OnClickListener listener = view -> {
            int visibility = back_wrap.getVisibility();

            if (visibility == View.VISIBLE) {
                AnimationTools.fadeOut(back_wrap);
            }

            // 启动图片选择 Activity
            Intent intent = new Intent(this, PhotoPickerActivity.class);
            startActivityForResult(intent, SharedData.ADD_EMOJI_RESULT);
            overridePendingTransition(R.anim.slide_in_top_medium, R.anim.stay_in_place_medium);
        };

        editor_emoji.setOnClickListener(listener);
    }

    public void initAddText() {
        RelativeLayout mask_view = findViewById(R.id.mask_view);
        RelativeLayout text_editor_mask = findViewById(R.id.text_editor_mask);

        EditText editText = findViewById(R.id.editor_edit);

        ImageButton editor_text = findViewById(R.id.editor_text);

        View.OnClickListener listener = view -> {
            AnimationTools.fadeIn(text_editor_mask);
            AnimationTools.fadeOut(mask_view);

            editTextFocus(editText);
        };

        editor_text.setOnClickListener(listener);
    }

    public void initCrop() {
        ImageButton editor_cut = findViewById(R.id.editor_cut);

        View.OnClickListener listener = view -> {
            editor.saveAsBitmap(new OnSaveBitmap() {
                @Override
                public void onBitmapReady(Bitmap bitmap) {
                    cut_source = saveBitmap(bitmap);
                    desCut_source = saveBitmap(bitmap);

                    UCrop uCrop = UCrop.of(getUri(cut_source), getUri(desCut_source));
                    UCrop.Options options = new UCrop.Options();
                    options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
                    options.setHideBottomControls(true);
                    options.setToolbarColor(getResources().getColor(R.color.mask));
                    options.setRootViewBackgroundColor(getResources().getColor(R.color.mask));
                    options.setStatusBarColor(getResources().getColor(R.color.mask));
                    options.setToolbarWidgetColor(Color.WHITE);
                    options.setFreeStyleCropEnabled(true);
                    uCrop.withOptions(options);
                    uCrop.start(PhotoEditorActivity.this);
                }

                @Override
                public void onFailure(Exception e) {}
            });
        };

        editor_cut.setOnClickListener(listener);
    }

    public void initUndoRedo() {
        ImageButton editor_retreat = findViewById(R.id.editor_retreat);
        ImageButton editor_redo = findViewById(R.id.editor_redo);

        editor_retreat.setOnClickListener(view -> editor.undo());
        editor_redo.setOnClickListener(view -> editor.redo());
    }

    public void initComplete() {
        Button editor_complete = findViewById(R.id.editor_complete);

        View.OnClickListener listener = view -> {
            editor.saveAsBitmap(new OnSaveBitmap() {
                @Override
                public void onBitmapReady(Bitmap saveBitmap) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putBinder("result", new BitmapBinder(saveBitmap));
                    intent.putExtra("result_bundle", bundle);
                    setResult(SharedData.DRAW_PICTURE_RESULT, intent);
                    finish();
                    overridePendingTransition(0, 0);
                }

                @Override
                public void onFailure(Exception e) {
                }
            });
        };

        editor_complete.setOnClickListener(listener);
    }

    // 初始化软键盘高度变化事件
    public void initKeyboardChange() {
        new Keyboard(this).onKeyboardHeightChanged(new Keyboard.OnKeyboardHeightChangeCallback() {
            @Override
            public void onChange(boolean visible, float keyboardHeight, float translateY) {
                getWindow().getDecorView().setPadding(0, 0, 0, Math.round(translateY));
            }
        });
    }

    // 初始化文本编辑事件
    public void initEdit() {
        LinearLayout editor_edit_wrap = findViewById(R.id.editor_edit_wrap);
        EditText editText = findViewById(R.id.editor_edit);

        View.OnClickListener listener = view -> editComplete(editText);
        editor_edit_wrap.setOnClickListener(listener);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                // 单行时居中对齐, 多行时左对齐
                editText.post(() -> {
                    if (editText.getLineCount() > 1) {
                        editText.setGravity(Gravity.START);
                    } else {
                        editText.setGravity(Gravity.CENTER);
                    }
                });
            }
        };

        editText.addTextChangedListener(watcher);
    }

    public void initTextEvent() {
        Button complete = findViewById(R.id.text_editor_complete);
        Button cancel = findViewById(R.id.text_editor_cancel);
        EditText editText = findViewById(R.id.editor_edit);

        complete.setOnClickListener(view -> editComplete(editText));

        cancel.setOnClickListener(view -> editCancel(editText));
    }

    // 初始化文本颜色选择
    public void initTextGraffitiColor() {
        LinearLayout layout = findViewById(R.id.text_graffiti_color);

        ArrayList<Integer> list = GraffitiColor.getColors();

        ArrayList<Concentric> concentrates = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
            Concentric concentric = new Concentric(this);

            concentric.setLayoutParams(params);

            concentric.setRadius(25);
            concentric.setColor(list.get(i));

            View.OnClickListener listener = view -> {
                for (int j = 0; j < concentrates.size(); j++) {
                    concentrates.get(j).setRadius(25);
                    concentrates.get(j).setStrokeWidth(6);
                }
                concentric.setRadius(30);
                concentric.setStrokeWidth(11);

                setEditorTextColor(concentric.getColor());
            };

            concentric.setOnClickListener(listener);

            if (concentric.getColor() == GraffitiColor.WHITE) {
                concentric.setRadius(30);
                concentric.setStrokeWidth(11);
                setEditorTextColor(concentric.getColor());
            }

            layout.addView(concentric);
            concentrates.add(concentric);
        }
    }

    public void editComplete(EditText editText) {
        RelativeLayout mask_view = findViewById(R.id.mask_view);
        RelativeLayout text_editor_mask = findViewById(R.id.text_editor_mask);

        editTextBlur(editText);

        String text = String.valueOf(editText.getText());
        if (!text.trim().equals("")) {
            textStyleBuilder.withTextSize(16);

            if (rootView != null) {
                editor.editText(rootView, text, textStyleBuilder);
            } else {
                editor.addText(text, textStyleBuilder);
            }
        }

        AnimationTools.fadeIn(mask_view);
        AnimationTools.fadeOut(text_editor_mask);
        editText.setText("");

        rootView = null;
    }

    public void editCancel(EditText editText) {
        RelativeLayout mask_view = findViewById(R.id.mask_view);
        RelativeLayout text_editor_mask = findViewById(R.id.text_editor_mask);

        editTextBlur(editText);
        editText.setText("");

        AnimationTools.fadeIn(mask_view);
        AnimationTools.fadeOut(text_editor_mask);

        rootView = null;
    }

    public void editTextFocus(EditText editText) {
        editText.requestFocus();
        Objects.requireNonNull(WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView())).show(WindowInsetsCompat.Type.ime());
    }

    public void editTextBlur(EditText editText) {
        editText.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive(editText)) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    public void setEditorTextColor(int _color) {
        EditText editText = findViewById(R.id.editor_edit);
        editText.setTextColor(_color);
        textStyleBuilder.withTextColor(_color);
    }

    // 设置画笔颜色
    public void setEditorShapeColor(int _color) {
        editor.setShape(new ShapeBuilder().withShapeColor(_color));
    }

    public File saveBitmap(Bitmap bitmap) {
        File cacheDir = getCacheDir();
        File temp = new File(cacheDir,  System.currentTimeMillis() + ".jpg");

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(temp));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Uri getUri(File file) {
        return Uri.fromFile(file);
    }
}
