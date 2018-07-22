package com.pbnj.pbnj.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by amgregoi on 7/22/18.
 */

public class RecipeItem
{
    public String id;
    public String description;
    public String title;
    public String image;

    public RecipeItem(JSONObject obj)
    {
        try
        {
            id = obj.getString("id");
            description = obj.getString("description");
            title = obj.getString("title");
            image = obj.getString("image_link");
        }
        catch (JSONException ex)
        {
            // oops
            ex.printStackTrace();
        }
    }
}
