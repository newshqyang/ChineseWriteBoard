package com.sw.chinesewriteboard.bll;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.os.Handler;

import com.sw.chinesewriteboard.dal.ChineseDataConfig;
import com.sw.chinesewriteboard.model.MyTypeface;
import com.sw.chinesewriteboard.utility.ChineseHandlerConstant;
import com.sw.chinesewriteboard.utility.FileUtils;

import java.util.List;

public class TypefaceOperate {

    private Context mContext;
    public TypefaceOperate(Context context) {
        mContext = context;
    }

    /*
    加载APP内置字体
     */
    private final String ASSET_FONT_FOLDER = "fonts/";
    public void initAppTypeface(List<MyTypeface> typefaceList) {
        typefaceList.add(new MyTypeface("楷体", Typeface.createFromAsset(mContext.getAssets(), ASSET_FONT_FOLDER + "楷体")));
        typefaceList.add(new MyTypeface("田英章楷书", Typeface.createFromAsset(mContext.getAssets(), ASSET_FONT_FOLDER + "田英章楷书")));
        typefaceList.add(new MyTypeface("隶书", Typeface.createFromAsset(mContext.getAssets(), ASSET_FONT_FOLDER + "隶书")));
    }

    /*
    扫描手机内的字体
     */
    public void searchPhoneTtfFiles(final List<MyTypeface> typefaceList, final Handler handler) {
        final String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        // 扫描开始
        handler.sendEmptyMessage(ChineseHandlerConstant.SEARCH_TTF_START);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (FileUtils.searchTtfFiles(rootPath, typefaceList)) {
                    // 扫描完成
                    handler.sendEmptyMessage(ChineseHandlerConstant.SEARCH_TTF_END);
                }
            }
        }).start();
    }

}
