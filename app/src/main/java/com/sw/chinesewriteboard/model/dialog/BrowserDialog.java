package com.sw.chinesewriteboard.model.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.sw.chinesewriteboard.R;
import com.sw.chinesewriteboard.model.MyWebView;

public class BrowserDialog extends MyDialog {
    public BrowserDialog(Context context) {
        super(context);
        init(context);
    }

    private MyWebView mWebView;
    private Button mClose;
    private void init(Context context) {
        setContentView(R.layout.layout_browser);
        mWebView = findViewById(R.id.webView_browser);
        mClose = findViewById(R.id.button_close);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private String character;
    public Dialog setCharacter(String character) {
        this.character = character;
        mWebView.loadUrl("https://hanyu.baidu.com/s?wd=" + character);
        return this;
    }
}
