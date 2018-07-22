package com.pbnj.pbnj.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pbnj.pbnj.Adapter.RecipeAdapter;
import com.pbnj.pbnj.R;
import com.pbnj.pbnj.RecyclerViewSpaceDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by amgregoi on 7/22/18.
 */

public class RecipeFragment extends Fragment
{
    public final static String TAG = RecipeFragment.class.getSimpleName();

    @BindView(R.id.recyclerViewRecipe) RecyclerView mRecyclerView;
    @BindView(R.id.buttonRecipeReturn) Button mReturnButton;

    public static Fragment newInstance()
    {
        return new RecipeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View lView = inflater.inflate(R.layout.fragment_recipe, null);
        ButterKnife.bind(this, lView);

        initViews();

        return lView;
    }

    private void initViews()
    {
        //Button text
        Typeface lFontCircular = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/CircularStd_Black.otf");
        mReturnButton.setTypeface(lFontCircular);

        //Adapter
        RecipeAdapter lAdapter = new RecipeAdapter();
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(lManager);
        mRecyclerView.setAdapter(lAdapter);
        mRecyclerView.addItemDecoration(new RecyclerViewSpaceDecoration(10));
    }

    @OnClick(R.id.buttonRecipeReturn)
    public void onReturnButton()
    {
        getActivity().onBackPressed();
    }
}
