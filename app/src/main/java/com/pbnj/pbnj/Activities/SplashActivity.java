package com.pbnj.pbnj.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.pbnj.pbnj.HomeCooked;
import com.pbnj.pbnj.Models.Meal;
import com.pbnj.pbnj.Util.RestClient;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by amgregoi on 7/21/18.
 */

public class SplashActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getNextShow();
    }

    private void getNextShow()
    {
        RestClient.getNextShow(new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                super.onSuccess(statusCode, headers, response);
                Meal lMeal = new Meal(response);
                HomeCooked.getInstance().setNextShow(lMeal);

                Intent lIntent = LoginActivity.newInstance(SplashActivity.this);
                startActivity(lIntent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(SplashActivity.this, "Problem retrieving show", Toast.LENGTH_LONG).show();
            }
        });
    }

}
