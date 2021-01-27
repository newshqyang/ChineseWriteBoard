package com.sw.chinesewriteboard.bll;

import android.content.Context;

import com.sw.chinesewriteboard.dal.ChineseDataConfig;

import java.util.List;

public class ChineseDataOperate {

    private final int COLUMN_LENGTH = ChineseDataConfig.TOTAL_LENGTH_COLUMN;
    private final int ROW_LENGTH = ChineseDataConfig.TOTAL_LENGTH_ROW;

    private Context mContext;
    public ChineseDataOperate(Context context) {
        mContext = context;
    }

    public String listToString(List<String> list) {
        StringBuilder temp = new StringBuilder();
        for (String string : list) {
            if (temp.toString().length() != 0 && temp.toString().length() % COLUMN_LENGTH > 0) {
                addSpace(COLUMN_LENGTH - temp.toString().length() % COLUMN_LENGTH, temp);   // 换行
            }
            if (string.length() <= COLUMN_LENGTH && !checkHasPunctuation(string)) { // 词组，自动复制
                switch (string.length()) {
                    case 4:
                        temp.append(string).append("  ").append(string);
                        break;
                    case 3:
                        temp.append(string).append("   ").append(string).append(" ");
                        break;
                    case 2:
                        temp.append(string).append("  ").append(string).append("  ").append(string);
                        break;
                    default:
                        temp.append(string).append(' ');
                        int times = COLUMN_LENGTH / (string.length() + 1) - 1;
                        while (times > 0) {
                            temp.append(string).append(' ');
                            times--;
                        }
                        break;
                }
            } else {    // 句子，开头空两格
                temp.append(' ').append(' ').append(string);
            }
        }
        // 字帖页面上的剩余空格补足
        int surplus = ChineseDataConfig.TOTAL_LENGTH - temp.toString().length() % ChineseDataConfig.TOTAL_LENGTH;
        addSpace(surplus, temp);
        return temp.toString();
    }

    /*
    往后增加空格
     */
    private StringBuilder addSpace(int surplus, StringBuilder temp) {
        while (surplus > 0) {
            temp.append(' ');
            surplus--;
        }
        return temp;
    }


    /*
    检查是否有标点符号
     */
    public boolean checkHasPunctuation(String s) {
        boolean b = false;

        String tmp = s;
        tmp = tmp.replaceAll("\\p{P}", "");
        if (s.length() != tmp.length()) {
            b = true;
        }

        return b;
    }
}
