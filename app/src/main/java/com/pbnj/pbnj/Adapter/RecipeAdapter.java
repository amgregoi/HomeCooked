package com.pbnj.pbnj.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pbnj.pbnj.HomeCooked;
import com.pbnj.pbnj.Models.RecipeStep;
import com.pbnj.pbnj.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amgregoi on 7/21/18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MessageHolder>
{
    private List<RecipeStep> mSteps;

    public RecipeAdapter()
    {
        mSteps = HomeCooked.getInstance().getNextShow().recipeSteps;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        MessageHolder lHolder = new MessageHolder(lView);
        return lHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position)
    {
        holder.onBind(position);
    }

    @Override
    public int getItemCount()
    {
        return mSteps.size();
    }

    /**************************************************
     *
     * View holders
     *
     **************************************************/
    public class MessageHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.textViewRecipeStepMessage) TextView mStepMessage;
        @BindView(R.id.textViewRecipeStepNumber) TextView mStepNumber;
        public MessageHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position)
        {
            RecipeStep lStep = mSteps.get(position);
            mStepMessage.setText(lStep.description);
            mStepNumber.setText(lStep.stepNumber);
        }
    }
}
