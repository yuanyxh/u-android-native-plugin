package com.yuanyxh.camera.utils;

import android.graphics.Color;

import java.util.ArrayList;

public class GraffitiColor {
    public static final int WHITE = Color.parseColor("#ffffff");
    public static final int BLACK = Color.parseColor("#000000");
    public static final int RED = Color.parseColor("#ff0000");
    public static final int YELLOW = Color.parseColor("#ffb636");
    public static final int GREEN = Color.parseColor("#00FF00");
    public static final int BLUE = Color.parseColor("#508cee");
    public static final int PURPLE = Color.parseColor("#7B68EE");

    public static ArrayList<Integer> getColors() {
        ArrayList<Integer> list = new ArrayList<>();

        list.add(WHITE);
        list.add(BLACK);
        list.add(RED);
        list.add(YELLOW);
        list.add(GREEN);
        list.add(BLUE);
        list.add(PURPLE);

        return list;
    }
}
