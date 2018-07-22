package com.pbnj.pbnj;

import android.app.Application;

import com.pbnj.pbnj.Models.Meal;

/**
 * Created by amgregoi on 7/21/18.
 */

public class HomeCooked extends Application
{
    private static HomeCooked mInstance;

    private Meal mNextShow;

    public HomeCooked()
    {
        mInstance = this;
    }

    public static synchronized HomeCooked getInstance()
    {
        return mInstance;
    }

    public void setNextShow(Meal meal)
    {
        mNextShow = meal;
    }

    public Meal getNextShow()
    {
        return mNextShow;
    }

}
