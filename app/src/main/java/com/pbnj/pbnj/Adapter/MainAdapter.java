package com.pbnj.pbnj.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pbnj.pbnj.Activities.PlayerActivity;
import com.pbnj.pbnj.R;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by amgregoi on 7/21/18.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolderBase>
{
    public final static int VIEW_TYPE_HEADER_TRANSPARENT = 0;
    public final static int VIEW_TYPE_HEADER = 1;
    public final static int VIEW_TYPE_ITEM = 2;

    private MainAdapterListener mListener;
    private boolean mLiveFlag = false;

    public MainAdapter(MainAdapterListener listener)
    {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ViewHolderBase lHolder;
        View lView;
        switch (viewType)
        {
            case VIEW_TYPE_HEADER_TRANSPARENT:
                lView = LayoutInflater.from(parent.getContext()).inflate(ViewHolderHeaderTransparent.RESOURCE, parent, false);
                lHolder = new ViewHolderHeaderTransparent(lView);
                break;
            case VIEW_TYPE_HEADER:
                lView = LayoutInflater.from(parent.getContext()).inflate(ViewHolderHeader.RESOURCE, parent, false);
                lHolder = new ViewHolderHeader(lView);
                break;
            default:
                lView = LayoutInflater.from(parent.getContext()).inflate(ViewHolderItem.RESOURCE, parent, false);
                lHolder = new ViewHolderItem(lView);
                break;

        }

        return lHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderBase holder, int position)
    {
        holder.onBind(position);
    }

    @Override
    public int getItemCount()
    {
        return 10;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == 0)
        {
            return VIEW_TYPE_HEADER_TRANSPARENT;
        }
        else if (position == 1)
        {
            return VIEW_TYPE_HEADER;
        }

        return VIEW_TYPE_ITEM;
    }

    /**************************************************
     *
     * View holders
     *
     **************************************************/

    public abstract class ViewHolderBase extends RecyclerView.ViewHolder
    {

        public ViewHolderBase(View itemView)
        {
            super(itemView);
        }

        public void onBind(int position)
        {
            // do nothing by default
        }
    }

    public class ViewHolderHeaderTransparent extends ViewHolderBase
    {
        public final static int RESOURCE = R.layout.item_main_transparent_header;

        public ViewHolderHeaderTransparent(View itemView)
        {
            super(itemView);
        }
    }

    public class ViewHolderHeader extends ViewHolderBase
    {
        public final static int RESOURCE = R.layout.item_main_header;

        @BindView(R.id.buttonMainJoin) Button mButtonJoin;
        @BindView(R.id.buttonMainShare) Button mButtonShare;

        @BindView(R.id.shareContainer) CardView mCardViewShare;

        @BindColor(R.color.blue) int mColorBlue;
        @BindColor(R.color.white) int mColorWhite;
        @BindColor(android.R.color.transparent) int mColorTransparent;
        @BindDrawable(R.drawable.blue_edge_round_bg) Drawable mDrawableShare;

        public ViewHolderHeader(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);

            Typeface lFontCircular = Typeface.createFromAsset(itemView.getContext().getAssets(),  "fonts/CircularStd_Black.otf");
            mButtonJoin.setTypeface(lFontCircular);
            mButtonShare.setTypeface(lFontCircular);

            if(mLiveFlag)
            {
                mButtonJoin.setVisibility(View.VISIBLE);
                mCardViewShare.setCardBackgroundColor(mColorTransparent);
                mButtonShare.setBackground(mDrawableShare);
                mButtonShare.setTextColor(mColorBlue);
            }
            else
            {
                mButtonJoin.setVisibility(View.GONE);
                mCardViewShare.setCardBackgroundColor(mColorBlue);
                mButtonShare.setBackgroundColor(mColorTransparent);
                mButtonShare.setTextColor(mColorWhite);

            }
        }

        @OnClick(R.id.buttonMainJoin)
        public void onJoinClicked()
        {
            Context lContext = mListener.getContext();
            lContext.startActivity(PlayerActivity.newInstance(lContext));
        }

        @OnClick(R.id.buttonMainShare)
        public void onShareClicked()
        {
            setLiveFlag(!mLiveFlag);
        }
    }

    public class ViewHolderItem extends ViewHolderBase
    {
        public final static int RESOURCE = R.layout.item_main;

        public ViewHolderItem(View itemView)
        {
            super(itemView);
        }

        @Override
        public void onBind(int position)
        {
            super.onBind(position);
        }
    }

    /**************************************************
     *
     * View holders
     *
     **************************************************/
    public interface MainAdapterListener
    {
        Context getContext();
    }

    /**************************************************
     *
     * Other
     *
     **************************************************/
    public void setLiveFlag(boolean flag)
    {
        mLiveFlag = flag;
        notifyItemChanged(1);
    }
}
