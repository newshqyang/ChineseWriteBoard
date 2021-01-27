package com.sw.chinesewriteboard.model;

import android.graphics.Bitmap;

public class MyColorBitmap {

    private String name;
    private int color;
    private Bitmap bitmap;

    public MyColorBitmap(String name, int color, Bitmap bitmap) {
        this.name = name;
        this.color = color;
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
