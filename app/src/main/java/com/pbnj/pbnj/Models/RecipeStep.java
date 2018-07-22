package com.pbnj.pbnj.Models;

/**
 * Created by amgregoi on 7/21/18.
 */

public class RecipeStep
{
    public int stepNumber;
    public String stepText;

    public RecipeStep(int step, String msg)
    {
        stepNumber = step;
        stepText = msg;
    }
}
