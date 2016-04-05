package com.a704084109qq.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.a704084109qq.news.R;
import com.a704084109qq.news.activity.MainActivity;

import java.util.List;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationViewholder> {
    private NavigationViewholder myViewholder;
    private int clickPositon;
    private List mList;
    private Context mContext;
    private int tag = 0;

    public NavigationAdapter(Context context, List list) {
        super();
        mContext = context;
        mList = list;
    }


    @Override
    public NavigationViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigation, null);
        return new NavigationViewholder(v);
    }

    @Override
    public void onBindViewHolder(NavigationViewholder holder, final int position) {

        holder.mTextView.setText((CharSequence) mList.get(position));
        holder.mTextView.setTag(position);

        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mContext).setFragment(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}

class NavigationViewholder extends RecyclerView.ViewHolder {
    public TextView mTextView;

    public NavigationViewholder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.item_navigation_tv);

    }
}

