package com.sw.chinesewriteboard.model.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sw.chinesewriteboard.R;
import com.sw.chinesewriteboard.model.MyTypeface;

import java.util.List;

public class TypefaceAdapter extends BaseAdapter {

    private Context mContext;
    private List<MyTypeface> mTypefaceList;
    public TypefaceAdapter(Context context, List<MyTypeface> typefaceList) {
        mContext = context;
        mTypefaceList = typefaceList;
    }

    @Override
    public int getCount() {
        return mTypefaceList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = View.inflate(mContext, R.layout.chip_typeface, null);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(mTypefaceList.get(position).getName());
        textView.setTypeface(mTypefaceList.get(position).getTypeface());
        return view;
    }
}
