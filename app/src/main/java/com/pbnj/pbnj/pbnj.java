package com.pbnj.pbnj;

import android.app.Application;

/**
 * Created by amgregoi on 7/21/18.
 */

public class pbnj extends Application
{
    static {
        System.loadLibrary("bambuser");
    }
}
