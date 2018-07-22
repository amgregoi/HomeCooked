package com.pbnj.pbnj.Widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by amgregoi on 7/21/18.
 */

public class ProspectMSBTextView extends AppCompatTextView
{
    public ProspectMSBTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public ProspectMSBTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public ProspectMSBTextView(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        if (!isInEditMode())
        {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ProspectusMSBd.otf");
            setTypeface(tf);
        }
    }
}
