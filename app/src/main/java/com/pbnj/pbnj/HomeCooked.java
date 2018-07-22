package com.pbnj.pbnj;

import android.app.Application;

/**
 * Created by amgregoi on 7/21/18.
 */

public class HomeCooked extends Application
{
    private static HomeCooked mInstance;

    public HomeCooked()
    {
        mInstance = this;
    }

    public static synchronized HomeCooked getInstance()
    {
        return mInstance;
    }

}
