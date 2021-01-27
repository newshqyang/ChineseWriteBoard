package com.sw.chinesewriteboard.model.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sw.chinesewriteboard.R;
import com.sw.chinesewriteboard.model.MyColor;

import java.util.List;

public class ColorAdapter extends BaseAdapter {

    private Context mContext;
    private List<MyColor> mMyColorList;
    public ColorAdapter(Context context, List<MyColor> colorList) {
        mContext = context;
        mMyColorList = colorList;
    }

    @Override
    public int getCount() {
        return mMyColorList.size();
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
        textView.setText(mMyColorList.get(position).getName());
        textView.setTextColor(mMyColorList.get(position).getColor());
        return view;
    }
}
