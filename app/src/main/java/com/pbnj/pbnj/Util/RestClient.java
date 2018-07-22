package com.pbnj.pbnj.Util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.Locale;

/**
 * Created by amgregoi on 7/22/18.
 */

public class RestClient extends AsyncHttpClient
{
    private static final String BASE_URL = "https://api.homecooked.live/api/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getNextShow(AsyncHttpResponseHandler responseHandler)
    {
        client.get(getAbsoluteUrl("show/next"), responseHandler);
    }

    public static String getAbsoluteUrl(String url)
    {
        return String.format(Locale.getDefault(), "%s%s", BASE_URL, url);
    }

}

