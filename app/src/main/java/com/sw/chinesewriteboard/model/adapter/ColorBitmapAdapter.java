package com.sw.chinesewriteboard.model.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sw.chinesewriteboard.R;
import com.sw.chinesewriteboard.model.MyColorBitmap;
import com.sw.chinesewriteboard.model.MyTypeface;

import java.util.List;

public class ColorBitmapAdapter extends BaseAdapter {

    private Context mContext;
    private List<MyColorBitmap> mMyColorBitmapList;
    public ColorBitmapAdapter(Context context, List<MyColorBitmap> colorBitmapList) {
        mContext = context;
        mMyColorBitmapList = colorBitmapList;
    }

    @Override
    public int getCount() {
        return mMyColorBitmapList.size();
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
        textView.setText(mMyColorBitmapList.get(position).getName());
        textView.setBackgroundColor(mMyColorBitmapList.get(position).getColor());
        return view;
    }
}
