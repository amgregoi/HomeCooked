package com.pbnj.pbnj.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by amgregoi on 7/22/18.
 */

public class Meal
{
    public String id;
    public String startTime; // date format
    public String title;
    public String description;
    public String image;
    public String runtime;
    public String chatId;
    public String currentStep;

    public List<RecipeItem> recipeItems;
    public List<RecipeStep> recipeSteps;

    public Meal(JSONObject obj)
    {
        try
        {
            id = obj.getString("id");
            startTime = obj.getString("start_time");
            title = obj.getString("title");
            description = obj.getString("description");
            image = obj.getString("show_image_header");
            runtime = obj.getString("runtime");
            chatId = obj.getString("chat_sid");
            currentStep = obj.getString("current_step_id");

            recipeItems = new ArrayList<>();
            JSONArray lRecipeItemArray = obj.getJSONArray("recipe_items");
            for (int i = 0; i < lRecipeItemArray.length(); i++)
            {
                RecipeItem lItem = new RecipeItem(lRecipeItemArray.getJSONObject(i));
                recipeItems.add(lItem);
            }

            recipeSteps = new ArrayList<>();
            JSONArray lRecipeStepArray = obj.getJSONArray("steps");
            for (int i = 0; i < lRecipeStepArray.length(); i++)
            {
                RecipeStep lStep = new RecipeStep(lRecipeStepArray.getJSONObject(i));
                recipeSteps.add(lStep);
            }
        }
        catch (JSONException jse)
        {
            //oops
            jse.printStackTrace();
        }
    }

    public String getMealTime()
    {
        try
        {

            SimpleDateFormat lInFormat = new SimpleDateFormat("yyyy-dd-mm hh:mm:ss");
            TimeZone lInZone = TimeZone.getTimeZone("UTC");
            lInZone.useDaylightTime();
            lInFormat.setTimeZone(lInZone);

            Date lDate = lInFormat.parse(startTime);

            SimpleDateFormat lOutFormat = new SimpleDateFormat("h:mma z");
            TimeZone lOutZone = TimeZone.getDefault();
            lOutZone.useDaylightTime();
            lOutFormat.setTimeZone(lOutZone);

            return lOutFormat.format(lDate).replace("AM", "am").replace("PM", "pm");
        }
        catch (ParseException ex)
        {
            // oops
            ex.printStackTrace();
        }

        return "TBD";
    }

    public RecipeStep getCurrentStep()
    {
        for (RecipeStep item : recipeSteps)
        {
            if (item.id.equals(currentStep))
            {
                return item;
            }
        }

        return recipeSteps.get(0); // default first step if doesn't exist
    }
}

