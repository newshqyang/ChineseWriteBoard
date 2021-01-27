package com.sw.chinesewriteboard.bll;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.sw.chinesewriteboard.R;
import com.sw.chinesewriteboard.model.ChineseData;
import com.sw.chinesewriteboard.utility.BitmapUtils;

public class CharacterOperation {

    public final int LEFTMOST = -1;
    public final int RIGHTMOST = 1;
    public final int NORMAL = 0;
    private int mIndex = NORMAL;  // 文字的位置，-1是最左边，0是中间，1是最右边

    public void setIndex(int index) {
        mIndex = index;
    }

    private Context mContext;
    public CharacterOperation(Context context) {
        mContext = context;
    }
    /*
    刷新样例外观
     */
    public void refreshAppearance(TextView threeLines, TextView character, ChineseData data) {
        set3LinesAppearance(threeLines, data);
        setCharacterAppearance(character, data);
    }

    /*
    设置田字格的外观
     */
    public void setCharacterAppearance(TextView character, ChineseData data) {
        character.setBackground(new BitmapDrawable(mContext.getResources(),
                BitmapUtils.tintBitmap(BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.tianzige_background), data.getBackgroundColor().getColor())));
        character.setTextColor(data.getFontColor().getColor());
        character.setTypeface(data.getMyTypeface().getTypeface());
    }

    /*
    设置三线格的外观
     */
    public void set3LinesAppearance(TextView threeLines, ChineseData data) {
        if (data == null) {
            System.out.println("日志：data为空");
        }
        Drawable bg = null;
        switch (mIndex) {
            case LEFTMOST:
                bg = new BitmapDrawable(mContext.getResources(),
                        BitmapUtils.tintBitmap(BitmapFactory.decodeResource(mContext.getResources(),
                                R.drawable.three_lines_background_leftmost), data.getBackgroundColor().getColor()));
                break;
            case NORMAL:
                bg = new BitmapDrawable(mContext.getResources(),
                        BitmapUtils.tintBitmap(BitmapFactory.decodeResource(mContext.getResources(),
                                R.drawable.three_lines_background), data.getBackgroundColor().getColor()));
                break;
            case RIGHTMOST:
                bg = new BitmapDrawable(mContext.getResources(),
                        BitmapUtils.tintBitmap(BitmapFactory.decodeResource(mContext.getResources(),
                                R.drawable.three_lines_background_rightmost), data.getBackgroundColor().getColor()));
                break;
        }
        threeLines.setBackground(bg);
        threeLines.setTextColor(data.getFontColor().getColor());
    }
}
