package com.sw.chinesewriteboard.bll;

import android.content.Context;

import com.sw.chinesewriteboard.R;
import com.sw.chinesewriteboard.model.MyColor;

import java.util.List;

public class ColorOperate {

    private Context mContext;
    public ColorOperate(Context context) {
        mContext = context;
    }

    public void initAppColor(List<MyColor> myColorList) {
        myColorList.add(new MyColor("红色", mContext.getColor(R.color.colorRed)));
        myColorList.add(new MyColor("黑色", mContext.getColor(R.color.colorBlack)));
        myColorList.add(new MyColor("淡绿色", mContext.getColor(R.color.colorLightGreen)));
        myColorList.add(new MyColor("灰色", mContext.getColor(R.color.colorGray)));
    }

}
