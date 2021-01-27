package com.sw.chinesewriteboard.model;

import android.graphics.Typeface;

public class MyFontFamily {

    private Typeface fontFamily;    // 字体
    private String fontFamilyName;  // 字体名称

    public Typeface getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(Typeface fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getFontFamilyName() {
        return fontFamilyName;
    }

    public void setFontFamilyName(String fontFamilyName) {
        this.fontFamilyName = fontFamilyName;
    }
}
