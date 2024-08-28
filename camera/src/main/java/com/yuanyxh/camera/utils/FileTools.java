package com.yuanyxh.camera.utils;

import java.io.File;
import java.util.ArrayList;

public class FileTools {
    public static ArrayList<File> getImagesOnPath(String path) {
        File file = new File(path);
        File[] files = file.listFiles();

        if (files == null) {
            return null;
        }

        ArrayList<File> result = new ArrayList<>();

        for (File value : files) {
            String absolutePath = value.getAbsolutePath();
            if (
                    absolutePath.endsWith(".bmp") ||
                            absolutePath.endsWith(".gif") ||
                            absolutePath.endsWith(".ico") ||
                            absolutePath.endsWith(".jpg") ||
                            absolutePath.endsWith(".jpeg") ||
                            absolutePath.endsWith(".png") ||
                            absolutePath.endsWith(".webp") ||
                            absolutePath.endsWith(".heic")
            ) {
                // int lastIndex = absolutePath.lastIndexOf("/");
                // 获取具体文件名称
                // String name = absolutePath.substring(lastIndex + 1);

                File image = new File(absolutePath);

                if (image.exists() && image.isFile()) {
                    result.add(image);
                }
            }
        }

        return result;
    }
}
