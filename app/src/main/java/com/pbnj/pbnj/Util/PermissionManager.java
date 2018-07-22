package com.pbnj.pbnj.Util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by amgregoi on 7/21/18.
 */

public class PermissionManager
{
    public final static int PERMISSION_REQUEST = 1;

    public static void getPermissions(Activity activity, String... permissions)
    {
        for (String iPerm : permissions)
        {
            if (!hasPermission(activity, iPerm))
            {
                ActivityCompat.requestPermissions(activity, new String[]{iPerm}, PERMISSION_REQUEST);
            }
        }
    }

    private static boolean hasPermission(Context context, String permission)
    {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
