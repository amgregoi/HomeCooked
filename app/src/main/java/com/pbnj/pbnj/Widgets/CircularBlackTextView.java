package com.pbnj.pbnj.Widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by amgregoi on 7/21/18.
 */

public class CircularBlackTextView extends AppCompatTextView
{
    public CircularBlackTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public CircularBlackTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public CircularBlackTextView(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        if (!isInEditMode())
        {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/CircularStd_Black.otf");
            setTypeface(tf);
        }
    }
}
