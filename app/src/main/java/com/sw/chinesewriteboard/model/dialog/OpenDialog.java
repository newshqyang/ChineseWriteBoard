package com.sw.chinesewriteboard.model.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sw.chinesewriteboard.R;
import com.sw.chinesewriteboard.utility.AndroidFileUtils;

public class OpenDialog extends Dialog {
    public OpenDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public OpenDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected OpenDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private TextView mTextViewFilePath;
    private Button mOpen;
    private void init(final Context context) {
        setContentView(R.layout.layout_open);
        mTextViewFilePath = findViewById(R.id.textView_filePath);
        mOpen = findViewById(R.id.button_open);
        mOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidFileUtils.openAndroidFile(context, mTextViewFilePath.getText().toString());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public Dialog setPath(String path) {
        mTextViewFilePath.setText(path);
        if (path.equals("保存失败")) {
            mOpen.setVisibility(View.GONE);
            mTextViewFilePath.setText(path + "\n请重新打开APP并授予读写权限");
        }
        return this;
    }
}
