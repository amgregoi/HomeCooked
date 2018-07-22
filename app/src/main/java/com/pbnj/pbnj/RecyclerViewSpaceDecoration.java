package com.pbnj.pbnj;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.pbnj.pbnj.HomeCooked;

/**
 * Created by amgregoi on 7/22/18.
 */

public class RecyclerViewSpaceDecoration extends RecyclerView.ItemDecoration
{
    private int mSpacing;

    public RecyclerViewSpaceDecoration(int space)
    {
        Resources r = HomeCooked.getInstance().getResources();
        mSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space, r.getDisplayMetrics());
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        int position = parent.getChildViewHolder(view).getAdapterPosition();
        int itemCount = state.getItemCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        setSpacingForDirection(outRect, layoutManager, position, itemCount);
    }

    private void setSpacingForDirection(Rect outRect, RecyclerView.LayoutManager layoutManager, int position, int itemCount)
    {

        if (layoutManager instanceof GridLayoutManager)
        {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int cols = gridLayoutManager.getSpanCount();
            int rows = itemCount / cols;

            outRect.left = mSpacing;
            outRect.right = position % cols == cols - 1 ? mSpacing : 0;
            outRect.top = mSpacing;
            outRect.bottom = position / cols == rows - 1 ? mSpacing : 0;
        }
        else if (layoutManager instanceof LinearLayoutManager)
        {
            outRect.left = mSpacing;
            outRect.right = mSpacing;
            outRect.top = mSpacing;
            outRect.bottom = position == itemCount - 1 ? mSpacing : 0;
        }
        else
        {
            outRect.left = mSpacing;
            outRect.right = position == itemCount - 1 ? mSpacing : 0;
            outRect.top = mSpacing;
            outRect.bottom = mSpacing;
        }
    }
}