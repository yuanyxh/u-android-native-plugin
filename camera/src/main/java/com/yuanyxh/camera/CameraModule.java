package com.yuanyxh.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.yuanyxh.camera.utils.BitmapBinder;
import com.yuanyxh.camera.utils.SharedData;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.dcloud.feature.uniapp.adapter.UniURIAdapter;
import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

public class CameraModule extends UniModule {
    private UniJSCallback callback = null;

    @UniJSMethod(uiThread = false)
    public void capture(JSONObject options, UniJSCallback _callback) {
        if (mUniSDKInstance.getContext() instanceof Activity) {
            callback = _callback;

            SharedData.emojiPath = (String) options.get("emojiPath");

            Intent intent = new Intent(mUniSDKInstance.getContext(), CameraActivity.class);
            ((Activity) mUniSDKInstance.getContext()).startActivityForResult(intent, SharedData.FINAL_PICTURE_RESULT);
            ((Activity) mUniSDKInstance.getContext()).overridePendingTransition(R.anim.slide_in_top, R.anim.stay_in_place);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SharedData.FINAL_PICTURE_RESULT && data != null) {
            Bundle bundle = data.getBundleExtra("final_result_bundle");

            if (bundle != null) {
                BitmapBinder binder = (BitmapBinder) bundle.getBinder("final_result");
                if (binder != null) {
                    Bitmap bitmap = binder.getBitmap();

                    if (bitmap != null) {
                        File file = saveBitmap(bitmap);
                        String path = "file://" + file.getAbsoluteFile();

                        if (callback != null) {
                            JSONObject object = new JSONObject();
                            object.put("imageFilePath", path);
                            callback.invoke(object);
                            callback = null;
                        }
                    }
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public File saveBitmap(Bitmap bitmap) {
        Uri uri = mUniSDKInstance.rewriteUri(Uri.parse("_doc/"), UniURIAdapter.FILE);

        File tempDir = new File(uri.getPath() + "uniapp_temp_" + System.currentTimeMillis());
        tempDir.mkdir();
        File temp = new File(tempDir.getAbsoluteFile() + "/" + System.currentTimeMillis() + ".jpg");

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
}
