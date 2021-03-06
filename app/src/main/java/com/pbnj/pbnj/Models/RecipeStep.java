package com.pbnj.pbnj.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by amgregoi on 7/21/18.
 */

public class RecipeStep
{
    public String id;
    public String description;
    public String title;
    public String stepNumber;


    public RecipeStep(int step, String desc)
    {
        stepNumber = ""+step;
        description = desc;
    }

    public RecipeStep(JSONObject obj)
    {
        try
        {
            id = obj.getString("id");
            description = obj.getString("description");
            title = obj.getString("title");
            stepNumber = obj.getString("step_number");
        }
        catch (JSONException ex)
        {
            // oops
            ex.printStackTrace();
        }
    }
}
