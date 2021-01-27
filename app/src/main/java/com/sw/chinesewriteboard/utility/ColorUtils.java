package com.sw.chinesewriteboard.utility;

import android.content.Context;
import android.graphics.Color;

public class ColorUtils {
    public static String getColorName(int color) {
        switch (color) {
            case Color.BLACK:
                return "黑色(字体)/淡绿色(背景)";
            case Color.RED:
                return "红色";
            case Color.TRANSPARENT:
                return "透明";
            case Color.GRAY:
                return "灰色";
        }
        return null;
    }
}
