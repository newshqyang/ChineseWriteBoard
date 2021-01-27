package com.sw.chinesewriteboard.model;

import android.content.Context;
import android.graphics.Typeface;

import com.sw.chinesewriteboard.R;

public class ChineseData {

    private String text;
    private MyTypeface myTypeface;
    private MyColor backgroundColor;
    private MyColor fontColor;

    public ChineseData(String text, MyTypeface typeface, MyColor backgroundColor, MyColor fontColor, Context context) {
        this.text = text;
        this.myTypeface = typeface;
        this.backgroundColor = backgroundColor;
        this.fontColor = fontColor;
        mContext = context;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MyTypeface getMyTypeface() {
        return myTypeface;
    }

    public void setMyTypeface(MyTypeface typeface) {
        this.myTypeface = typeface;
    }

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

    private final String ASSET_FONT_FOLDER = "fonts/";
    private Context mContext;
    public ChineseData(Context context) {
        mContext = context;
        setBackgroundColor(new MyColor("红色", mContext.getColor(R.color.colorRed)));
        setFontColor(new MyColor("灰色", mContext.getColor(R.color.colorGray)));
        setMyTypeface(new MyTypeface("楷体", Typeface.createFromAsset(mContext.getAssets(), ASSET_FONT_FOLDER + "楷体")));
    }
}
