package com.sw.chinesewriteboard.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.sw.chinesewriteboard.R;
import com.sw.chinesewriteboard.bll.CharacterOperation;
import com.sw.chinesewriteboard.bll.ChineseDataOperate;
import com.sw.chinesewriteboard.bll.ColorOperate;
import com.sw.chinesewriteboard.bll.TypefaceOperate;
import com.sw.chinesewriteboard.model.ChineseData;
import com.sw.chinesewriteboard.model.MyTypeface;
import com.sw.chinesewriteboard.model.adapter.ColorAdapter;
import com.sw.chinesewriteboard.model.adapter.PhraseAdapter;
import com.sw.chinesewriteboard.model.adapter.TypefaceAdapter;
import com.sw.chinesewriteboard.model.MyColor;
import com.sw.chinesewriteboard.model.dialog.ShowDialog;
import com.sw.chinesewriteboard.utility.BitmapUtils;
import com.sw.chinesewriteboard.utility.ChineseHandlerConstant;
import com.sw.chinesewriteboard.utility.UUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mCancel;
    private EditText mTextInput;
    private Button mAdd;
    private RecyclerView mPhraseBox;
    private Spinner mSpinnerBackgroundColor;
    private Spinner mSpinnerFontColor;
    private Spinner mSpinnerTypeface;
    private TextView mSample3Lines;
    private TextView mSampleCharacter;
    private Button mStartCreate;
    private Switch mPinyinSwitch;

    private List<String> mTextList; // 用来存储录入的词组或者句子的列表

    private ChineseData mData; // 所有数据
    private CharacterOperation mCharacterOperation; // 字体操作类
    private ChineseDataOperate mChineseDataOperate; // 文字数据操作类
    private ColorOperate mColorOperate; // 颜色操作
    private List<MyColor> mColorList;   // 颜色列表
    private TypefaceOperate mTypefaceOperate;   // 字体操作
    private List<MyTypeface> mTypefaceList; // 字体对象列表
    private TypefaceAdapter mTypefaceAdapter;   // 字体适配器
    private TextView mSearchLoadingTextView;    // 扫描字体文件时的提示语
    private ProgressBar mSearchLoadingBar;   // 扫描字体文件时的提示

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case ChineseHandlerConstant.REFRESH_RECYCLER_VIEW:
                    mPhraseBox.setAdapter(new PhraseAdapter(mTextList, MainActivity.this, mHandler));
                    break;
                case ChineseHandlerConstant.REFRESH_SAMPLE:
                    refreshColor();
                    break;
                case ChineseHandlerConstant.SEARCH_TTF_START:
                    mSpinnerTypeface.setEnabled(false);
                    mSearchLoadingTextView.setVisibility(View.VISIBLE);
                    mSearchLoadingBar.setVisibility(View.VISIBLE);
                    break;
                case ChineseHandlerConstant.SEARCH_TTF_END:
                    mTypefaceAdapter.notifyDataSetChanged();  // 绑定适配器
                    mSpinnerTypeface.setEnabled(true);
                    mSearchLoadingTextView.setVisibility(View.INVISIBLE);
                    mSearchLoadingBar.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };

    private void refreshColor() {
        mCharacterOperation.refreshAppearance(mSample3Lines, mSampleCharacter, mData);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UUtils.getReadWritePermissions(this);

        bindComponent();
        initialOthers();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == UUtils.REQUEST_PERMISSION_CODE) {
            initialOthers();
        }
    }

    /*
        加载其它的部分
         */
    private void initialOthers() {
        mTextList = new ArrayList<>();
        mData = new ChineseData(this);
        mCharacterOperation = new CharacterOperation(this);
        mChineseDataOperate = new ChineseDataOperate(this);

        // 颜色操作
        mColorOperate = new ColorOperate(this);
        mColorList = new ArrayList<>();
        mColorOperate.initAppColor(mColorList);
        mSpinnerBackgroundColor.setAdapter(new ColorAdapter(this, mColorList));
        mSpinnerFontColor.setAdapter(new ColorAdapter(this, mColorList));

        // 字体操作
        mTypefaceList = new ArrayList<>();      // 实例化字体对象列表
        mTypefaceOperate = new TypefaceOperate(this);   // 实例化字体操作对象
        mTypefaceOperate.initAppTypeface(mTypefaceList);        // 装填app内置字体
        mTypefaceAdapter = new TypefaceAdapter(this, mTypefaceList);
        mSpinnerTypeface.setAdapter(mTypefaceAdapter);  // 绑定适配器
        mSearchLoadingTextView = findViewById(R.id.textView_searching);
        mSearchLoadingBar = findViewById(R.id.progressBar_searching);
        mTypefaceOperate.searchPhoneTtfFiles(mTypefaceList, mHandler);

        mCharacterOperation.refreshAppearance(mSample3Lines, mSampleCharacter, mData);
    }

    /*
    绑定组件
     */
    private void bindComponent() {
        mCancel = findViewById(R.id.button_exit);
        mCancel.setOnClickListener(this);
        mPinyinSwitch = findViewById(R.id.pinyin_switch);
        mPinyinSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mSample3Lines.setVisibility(View.VISIBLE);
                } else {
                    mSample3Lines.setVisibility(View.GONE);
                }
            }
        });
        mTextInput = findViewById(R.id.editText_phrase);
        mAdd = findViewById(R.id.button_addNewPhrase);
        mAdd.setOnClickListener(this);
        mPhraseBox = findViewById(R.id.recyclerView_phrase);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mPhraseBox.setLayoutManager(manager);
        mSpinnerBackgroundColor = findViewById(R.id.spinner_backgroundColor);
        mSpinnerBackgroundColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mData.setBackgroundColor(mColorList.get(position));
                mHandler.sendEmptyMessage(ChineseHandlerConstant.REFRESH_SAMPLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerFontColor = findViewById(R.id.spinner_fontColor);
        mSpinnerFontColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mData.setFontColor(mColorList.get(position));
                mHandler.sendEmptyMessage(ChineseHandlerConstant.REFRESH_SAMPLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerTypeface = findViewById(R.id.spinner_familyLib);
        mSpinnerTypeface.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mData.setMyTypeface(mTypefaceList.get(position));
                mHandler.sendEmptyMessage(ChineseHandlerConstant.REFRESH_SAMPLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSample3Lines = findViewById(R.id.textView_pinyin);
        mSampleCharacter = findViewById(R.id.textView_character);
        mStartCreate = findViewById(R.id.button_startCreate);
        mStartCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_exit:
                finish();
                break;
            case R.id.button_addNewPhrase:
                addNewText();
                break;
            case R.id.button_startCreate:
            {
                if (mTextList.size() == 0) {
                    Toast.makeText(MainActivity.this, "请先录入词组或者句子", Toast.LENGTH_SHORT).show();
                    return;
                }
                mData.setText(mChineseDataOperate.listToString(mTextList));
                new ShowDialog(this)
                        .setPinyinEnabled(mPinyinSwitch.isChecked())
                        .setChineseData(mData)
                        .show();
            }
            break;
            default:
        }
    }

    /*
    加入新词组或者句子
     */
    private void addNewText() {
        String phrase = mTextInput.getText().toString();
        if (phrase.length() == 0) {
            phrase = " ";
            Toast.makeText(this, "插入一段空行", Toast.LENGTH_SHORT).show();
        }
        mTextList.add(phrase);
        mHandler.sendEmptyMessage(ChineseHandlerConstant.REFRESH_RECYCLER_VIEW);
        mTextInput.setText("");
        System.out.println("日志：加入了一个" + mTextList.size());
    }
}
