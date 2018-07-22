package com.pbnj.pbnj.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pbnj.pbnj.Adapter.MainAdapter;
import com.pbnj.pbnj.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amgregoi on 7/21/18.
 */

public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.recyclerViewMain) RecyclerView mRecyclerView;

    public static Intent newInstance(Context context)
    {
        Intent lIntent = new Intent(context, MainActivity.class);
        lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return lIntent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews()
    {
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this);
        MainAdapter lAdapter = new MainAdapter(new MainAdapter.MainAdapterListener()
        {
            @Override
            public Context getContext()
            {
                return MainActivity.this;
            }
        });

        mRecyclerView.setLayoutManager(lManager);
        mRecyclerView.setAdapter(lAdapter);
    }

}
