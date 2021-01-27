package com.sw.chinesewriteboard.model;

import android.graphics.Typeface;

public class MyTypeface {

    private String name;
    private Typeface typeface;

    public MyTypeface(String name, Typeface typeface) {
        this.name = name;
        this.typeface = typeface;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }
}
