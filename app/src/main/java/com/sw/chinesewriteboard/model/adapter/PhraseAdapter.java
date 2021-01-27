package com.sw.chinesewriteboard.model.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sw.chinesewriteboard.R;
import com.sw.chinesewriteboard.utility.ChineseHandlerConstant;

import java.util.List;

public class PhraseAdapter extends RecyclerView.Adapter<PhraseAdapter.PhraseViewHolder> {

    private List<String> mPhraseList;
    private Context mContext;
    private Handler mHandler;
    public PhraseAdapter(List<String> phraseList, Context context, Handler handler) {
        mPhraseList = phraseList;
        mContext = context;
        mHandler = handler;
    }

    @NonNull
    @Override
    public PhraseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhraseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.chip_phrase, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhraseViewHolder holder, final int position) {
        holder.phrase.setText(mPhraseList.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhraseList.remove(position);
                mHandler.sendEmptyMessage(ChineseHandlerConstant.REFRESH_RECYCLER_VIEW);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPhraseList.size();
    }

    class PhraseViewHolder extends RecyclerView.ViewHolder {

        private TextView phrase;
        private ImageButton delete;
        public PhraseViewHolder(@NonNull View itemView) {
            super(itemView);
            phrase = itemView.findViewById(R.id.textView_phrase);
            delete = itemView.findViewById(R.id.imageButton_delete);
        }
    }
}
