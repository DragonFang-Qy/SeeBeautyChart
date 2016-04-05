package com.a704084109qq.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a704084109qq.news.R;
import com.a704084109qq.news.model.BeautyPicModel;
import com.a704084109qq.news.model.Item;
import com.fangzitcl.libs.util.UtilLog;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BeautyPicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected int footerViewRes;
    protected boolean hasFooter = false;
    private Context mContext;
    private List<BeautyPicModel> mList;
    private ImageOptions imageOptions;
    private ItemClickListener itemClickListener;
    private ItemLongClickListener itemLongClickListener;

    public BeautyPicAdapter(Context context, List<BeautyPicModel> mListView, int footerViewRes) {
        super();
//        http://blog.csdn.net/sbsujjbcy/article/details/50112391
        mContext = context;
        mList = mListView;

        imageOptions = new ImageOptions.Builder()
                .setUseMemCache(true)
                .setPlaceholderScaleType(ImageView.ScaleType.FIT_CENTER)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(com.fangzitcl.libs.R.mipmap.ic_launcher)
                .setFailureDrawableId(com.fangzitcl.libs.R.mipmap.ic_launcher)
                .build();
        setFooterView(footerViewRes);
    }

    public boolean isHasFooter() {
        return hasFooter;
    }

    public boolean isFooter(int position) {
        return hasFooter() && position == mList.size();
    }

    public int getFooterView() {
        return footerViewRes;
    }

    public void setFooterView(int footerViewRes) {
        if (footerViewRes != 0) {
            if (!hasFooter()) {
                this.footerViewRes = footerViewRes;
                this.hasFooter = true;
                notifyItemInserted(mList.size());
            } else {
                this.footerViewRes = footerViewRes;
                notifyDataSetChanged();
            }

        } else {
            if (hasFooter()) {
                this.hasFooter = false;
                notifyItemRemoved(mList.size());
            }

        }

    }


    public boolean hasFooter() {
        return hasFooter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (hasFooter() && viewType == Item.TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(getFooterView(), parent, false);
            return new FooterViewHolder(v);
        } else {
            return new MyViewholder(LayoutInflater.from(mContext).inflate(R.layout.item_beautypic, null));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position) == Item.TYPE_FOOTER) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            View footerView = footerHolder.itemView;
        } else {
            x.image().bind(((MyViewholder) holder).image, mList.get(position).getPicUrl(), imageOptions);
            ((MyViewholder) holder).title.setText(mList.get(position).getTitle());

            ((MyViewholder) holder).image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClickListener(v, position);
                }
            });
            ((MyViewholder) holder).image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemLongClickListener.onItemLongClickListener(v, position);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        int count = 0;
        count += (hasFooter() ? 1 : 0);
        count += mList.size();
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()) {
            return Item.TYPE_FOOTER;
        } else {
            return mList.get(position).getType();
        }

    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isStaggeredGridLayout(holder)) {
            handleLayoutIfStaggeredGridLayout(holder, holder.getLayoutPosition());
        }
    }

    private boolean isStaggeredGridLayout(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            return true;
        }
        return false;
    }

    protected void handleLayoutIfStaggeredGridLayout(RecyclerView.ViewHolder holder, int position) {
        if (isFooter(position)) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.setFullSpan(true);
        }
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        itemClickListener = listener;
    }

    public interface ItemClickListener {
        void onItemClickListener(View v, int position);
    }

    public void setOnItemLongClickListener(ItemLongClickListener listener) {
        itemLongClickListener = listener;
    }

    public interface ItemLongClickListener {
        void onItemLongClickListener(View v, int position);
    }
}

class MyViewholder extends RecyclerView.ViewHolder {
    @Bind(R.id.my_image_view)
    public ImageView image;

    @Bind(R.id.title)
    public TextView title;

    public MyViewholder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}

class FooterViewHolder extends RecyclerView.ViewHolder {
    public FooterViewHolder(View itemView) {
        super(itemView);
    }
}

