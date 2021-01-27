package com.sw.chinesewriteboard.model.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sw.chinesewriteboard.R;
import com.sw.chinesewriteboard.model.MyColor;
import com.sw.chinesewriteboard.utility.ChineseHandlerConstant;

public class ColorSelectDialog extends Dialog {
    public ColorSelectDialog(@NonNull Context context) {
        super(context);
        initial();
    }

    public ColorSelectDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initial();
    }

    protected ColorSelectDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initial();
    }

    private RadioGroup mColorRadioGroup;
    public void initial() {
        setContentView(R.layout.layout_select_color);
        mColorRadioGroup = findViewById(R.id.radioGroup_color);
        mColorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_red:
                        mColor.setColor(Color.RED);
                        break;
                    case R.id.radioButton_gray:
                        mColor.setColor(Color.GRAY);
                        break;
                    case R.id.radioButton_black:
                        mColor.setColor(Color.BLACK);
                        break;
                    case R.id.radioButton_transparent:
                        mColor.setColor(Color.TRANSPARENT);
                        break;
                }
                mHandler.sendEmptyMessage(ChineseHandlerConstant.REFRESH_SAMPLE);
                dismiss();
            }
        });
    }

    private MyColor mColor;
    public ColorSelectDialog setMyColor(MyColor color) {
        mColor = color;
        return this;
    }

    private Handler mHandler;
    public ColorSelectDialog setHandler(Handler handler) {
        mHandler = handler;
        return this;
    }
}
