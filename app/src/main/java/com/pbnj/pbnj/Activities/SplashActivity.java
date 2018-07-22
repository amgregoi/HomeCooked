package com.pbnj.pbnj.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by amgregoi on 7/21/18.
 */

public class SplashActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent lIntent = LoginActivity.newInstance(this);
//        Intent lIntent = BroadcastActivity.newInstance(this);
        startActivity(lIntent);
    }
}
