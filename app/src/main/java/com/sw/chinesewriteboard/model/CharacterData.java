package com.sw.chinesewriteboard.model;

import android.graphics.Typeface;

public class CharacterData {
    public MyColor backgroundColor;
    public MyColor fontColor;
    public Typeface fontFamily;    // 字体
    public String fontFamilyName;  // 字体名称

    public MyColor getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(MyColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public MyColor getFontColor() {
        return fontColor;
    }

    public void setFontColor(MyColor fontColor) {
        this.fontColor = fontColor;
    }

    public void setFontFamily(Typeface fontFamily) {
        this.fontFamily = fontFamily;
    }

    public Typeface getFontFamily() {
        return fontFamily;
    }

    public String getFontFamilyName() {
        return fontFamilyName;
    }

    public void setFontFamilyName(String fontFamilyName) {
        this.fontFamilyName = fontFamilyName;
    }
}
