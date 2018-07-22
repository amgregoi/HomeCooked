package com.pbnj.pbnj.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbnj.pbnj.HomeCooked;
import com.pbnj.pbnj.Models.RecipeStep;
import com.pbnj.pbnj.R;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by amgregoi on 7/22/18.
 */

public class RecipePagerAdapter extends PagerAdapter
{
    private List<RecipeStep> mSteps;
    private SingleClickListener mListener;

    public RecipePagerAdapter(SingleClickListener listener)
    {
        mSteps = HomeCooked.getInstance().getNextShow().recipeSteps;
        mListener = listener;
    }

    @Override
    public int getCount()
    {
        return mSteps.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        RecipeStep lStep = mSteps.get(position);

        RelativeLayout lView = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.item_recipe_steps, null);
        lView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mListener.onSingleClick();
            }
        });

        TextView mDescription = lView.findViewById(R.id.textViewStepText);
        TextView mStepNumber = lView.findViewById(R.id.textViewStepNumber);

        mDescription.setText(lStep.description);
        mStepNumber.setText(lStep.stepNumber);

        container.addView(lView);

        return lView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((RelativeLayout)object);
    }

    public interface SingleClickListener
    {
        void onSingleClick();
    }
}
