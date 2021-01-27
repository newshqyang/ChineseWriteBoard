package com.sw.chinesewriteboard.model.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sw.chinesewriteboard.R;
import com.sw.chinesewriteboard.model.MyFontFamily;
import com.sw.chinesewriteboard.utility.ChineseHandlerConstant;

public class FontSelectDialog extends Dialog {

    private final String ASSET_FONT_FOLDER = "fonts/";

    public FontSelectDialog(@NonNull Context context) {
        super(context);
        initial();
    }

    public FontSelectDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initial();
    }

    protected FontSelectDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initial();
    }

    private RadioGroup mColorRadioGroup;
    public void initial() {
        setContentView(R.layout.layout_select_font);
        mColorRadioGroup = findViewById(R.id.radioGroup_font);
        mColorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String fontFamilyName = "";
                switch (checkedId) {
                    case R.id.radioButton_tianyingzhangkaishu:
                        fontFamilyName = "田英章楷书";
                        mFontFamily.setFontFamily(Typeface.createFromAsset(getContext().getAssets(), ASSET_FONT_FOLDER + fontFamilyName));
                        mFontFamily.setFontFamilyName(fontFamilyName);
                        break;
                    case R.id.radioButton_kaiti:
                        fontFamilyName = "楷体";
                        mFontFamily.setFontFamily(Typeface.createFromAsset(getContext().getAssets(), ASSET_FONT_FOLDER + fontFamilyName));
                        mFontFamily.setFontFamilyName(fontFamilyName);
                        break;
                    case R.id.radioButton_lishu:
                        fontFamilyName = "隶书";
                        mFontFamily.setFontFamily(Typeface.createFromAsset(getContext().getAssets(), ASSET_FONT_FOLDER + fontFamilyName));
                        mFontFamily.setFontFamilyName(fontFamilyName);
                        break;
                }
                mHandler.sendEmptyMessage(ChineseHandlerConstant.REFRESH_SAMPLE);
                dismiss();
            }
        });

    }

    private MyFontFamily mFontFamily;
    public FontSelectDialog setData(MyFontFamily fontFamily) {
        mFontFamily = fontFamily;
        return this;
    }

    private Handler mHandler;
    public FontSelectDialog setHandler(Handler handler) {
        mHandler = handler;
        return this;
    }
}
