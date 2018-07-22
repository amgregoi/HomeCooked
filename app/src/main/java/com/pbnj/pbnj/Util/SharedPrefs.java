package com.pbnj.pbnj.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.pbnj.pbnj.HomeCooked;
import com.pbnj.pbnj.R;

/**
 * Created by amgregoi on 7/21/18.
 */

public class SharedPrefs
{
    /**
     * This function retrieves the stored user name from the device.
     *
     * @return The users name.
     */
    public static String getUserName()
    {
        Context lContext = HomeCooked.getInstance();
        return PreferenceManager.getDefaultSharedPreferences(lContext)
                                .getString(lContext.getString(R.string.PREF_USER_NAME), null);
    }

    /**
     * This function saves the current users name to the device.
     *
     * @param name The users name.
     */
    public static void setUserName(String name)
    {
        Context lContext = HomeCooked.getInstance();
        SharedPreferences.Editor lEditor = PreferenceManager.getDefaultSharedPreferences(lContext).edit();
        lEditor.putString(lContext.getString(R.string.PREF_USER_NAME), name);
        lEditor.apply();
    }
}
