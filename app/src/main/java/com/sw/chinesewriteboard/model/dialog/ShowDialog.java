package com.sw.chinesewriteboard.model.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.sw.chinesewriteboard.R;
import com.sw.chinesewriteboard.bll.CharacterOperation;
import com.sw.chinesewriteboard.dal.ChineseDataConfig;
import com.sw.chinesewriteboard.model.ChineseData;
import com.sw.chinesewriteboard.utility.UUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowDialog extends MyDialog implements View.OnClickListener {

    private Context mContext;

    private int COLUMN_LENGTH = ChineseDataConfig.TOTAL_LENGTH_COLUMN;
    private int ROW_LENGTH = ChineseDataConfig.TOTAL_LENGTH_ROW;

    private int mPage = 0; // 当前页数
    private int PAGE_LENGTH = 1;    // 总页数
    private Map<String, String> mCharacterMap;  // 汉字-拼音字典

    private LinearLayout mWordPaper;
    private LinearLayout mWordList;

    private ChineseData mData;
    public ShowDialog setChineseData(ChineseData chineseData) {
        mData = chineseData;
        PAGE_LENGTH = mData.getText().length() / ChineseDataConfig.TOTAL_LENGTH;    // 计算总共页数
        int surplus = mData.getText().length() % ChineseDataConfig.TOTAL_LENGTH;
        if (surplus > 0) {
            PAGE_LENGTH++;
        }
        createWordView();
        return this;
    }

    public ShowDialog(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setContentView(R.layout.layout_show);
        mContext = context;
        initialOthers();
        mWordPaper = findViewById(R.id.horizontalScrollView_wordPaper);
        findViewById(R.id.button_previousPage).setOnClickListener(this);
        findViewById(R.id.button_nextPage).setOnClickListener(this);
        findViewById(R.id.button_save).setOnClickListener(this);
        findViewById(R.id.button_cancel).setOnClickListener(this);
        mWordList = findViewById(R.id.linearLayout_list);
    }

    /*
   加载其它部分
    */
    private CharacterOperation mCharacterOperation;
    private List<EditText> mEditTextList;
    private void initialOthers() {
        mEditTextList = new ArrayList<>();
        mCharacterOperation = new CharacterOperation(mContext);
        mCharacterMap = new HashMap<>();
        try {
            InputStream is = mContext.getAssets().open("key.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line=br.readLine()) != null) {
                String[] temp = line.split(",");
                mCharacterMap.put(temp[0], temp[1]);
            }
            br.close();
            is.close();
        } catch (IOException e) {
            System.out.println("日志：加载字典失败");
            e.printStackTrace();
        }
    }

    /*
    打印
     */
    private void createWordView() {
        mWordList.removeAllViews();
        mEditTextList.clear();
        int startIndex = ChineseDataConfig.TOTAL_LENGTH * mPage;    // 字符串的起始坐标
        int k = startIndex;
        System.out.println("日志：p：" + mPage + "    k:" + k);
        for (int i = 0;i < ROW_LENGTH;i++) {
            LinearLayout row = new LinearLayout(mContext);
            row.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0;j < COLUMN_LENGTH;j++) {
                if (j == 0) {
                    mCharacterOperation.setIndex(mCharacterOperation.LEFTMOST);
                } else if (j == COLUMN_LENGTH - 1) {
                    mCharacterOperation.setIndex(mCharacterOperation.RIGHTMOST);
                } else {
                    mCharacterOperation.setIndex(mCharacterOperation.NORMAL);
                }
                row.addView(createCharacterLayout(mData.getText().charAt(k), mData));
                k++;
            }
            mWordList.addView(row);
        }
    }
    /*
    创建单个字符，带三线格
     */
    private final int PINYIN_LENGTH_MAX_1 = 6;
    private final float PINYIN_FONT_SIZE_MAX_1 = 25;
    private final int PINYIN_LENGTH_MAX_2 = 5;
    private final float PINYIN_FONT_SIZE_MAX_2 = 30;
    @SuppressLint("SetTextI18n")
    private View createCharacterLayout(char c, ChineseData data) {
        View characterLayout = View.inflate(mContext, R.layout.layout_character, null);
        final EditText pinyin = characterLayout.findViewById(R.id.editText_pinyin);
        String py = mCharacterMap.get("" + c);
        if (py != null) {
            pinyin.setText(py);
            if (py.length() == PINYIN_LENGTH_MAX_1) {
                pinyin.setTextSize(PINYIN_FONT_SIZE_MAX_1);
            } else if (py.length() == PINYIN_LENGTH_MAX_2) {
                pinyin.setTextSize(PINYIN_FONT_SIZE_MAX_2);
            }
        } else {
            pinyin.setText(" ");
        }
        mCharacterOperation.set3LinesAppearance(pinyin, data);
        final EditText character = characterLayout.findViewById(R.id.editText_character);
        character.setText("" + c);
        mCharacterOperation.setCharacterAppearance(character, data);
        pinyin.setCustomSelectionActionModeCallback(new ActionMode.Callback2() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.selection_action_menu,menu);
                return true;//返回false则不会显示弹窗
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                //根据item的ID处理点击事件
                if (item.getItemId() == R.id.search_pinyin) {
                    new BrowserDialog(mContext).setCharacter(character.getText().toString()).show();
                    Toast.makeText(mContext, "友情提示：长按复制拼音", Toast.LENGTH_SHORT).show();
                    mode.finish();//收起操作菜单
                }
                return false;//返回true则系统的"复制"、"搜索"之类的item将无效，只有自定义item有响应
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        mEditTextList.add(pinyin);
        mEditTextList.add(character);
        return characterLayout;
    }

    private final int LIST_ENABLE_FALSE = 0;
    private final int LIST_ENABLE_TRUE = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case LIST_ENABLE_FALSE:
                    for (EditText editText : mEditTextList) {
                        editText.setEnabled(false);
                    }
                    break;
                case LIST_ENABLE_TRUE:
                    for (EditText editText : mEditTextList) {
                        editText.setEnabled(true);
                    }
                    break;
            }
        }
    };

    /*
    下载字帖
     */
    private Dialog mLoading;
    private void saveBitmap() {
        mHandler.sendEmptyMessage(LIST_ENABLE_FALSE);
        if (mLoading == null) {
            mLoading = new AlertDialog.Builder(mContext)
                    .setTitle("正在保存...")
                    .setCancelable(false)
                    .create();
        }
        mLoading.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap img = loadBitmapFromView();
                String filePath = UUtils.saveBmImg(img);
                mHandler.sendEmptyMessage(LIST_ENABLE_TRUE);
                Looper.prepare();
                new OpenDialog(mContext).setPath(filePath).show();
                mLoading.dismiss();
                if (!filePath.equals("保存失败")) {
                    Toast.makeText(mContext, "第 " + (mPage + 1) + "页已保存", Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            }
        }).start();
    }
    private Bitmap loadBitmapFromView()  {
        int w = mWordPaper.getWidth();
        int h = mWordList.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /* 如果不设置canvas画布为白色，则生成透明 */
        mWordList.layout(0, 0, w, h);
        mWordList.draw(c);

        return bmp;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save:
                saveBitmap();
                break;
            case R.id.button_cancel:
                dismiss();
                break;
            case R.id.button_previousPage:
                mPage--;
                if (mPage < 0) {
                    mPage = 0;
                    Toast.makeText(mContext, "已经是第一页了", Toast.LENGTH_SHORT).show();
                    break;
                }
                createWordView();
                break;
            case R.id.button_nextPage:
                mPage++;
                if (mPage >= PAGE_LENGTH) {
                    mPage = PAGE_LENGTH - 1;
                    Toast.makeText(mContext, "已经是最后一页了", Toast.LENGTH_SHORT).show();
                    break;
                }
                createWordView();
                break;
        }
    }
}
