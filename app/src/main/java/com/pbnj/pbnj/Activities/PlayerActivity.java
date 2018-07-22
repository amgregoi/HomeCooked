package com.pbnj.pbnj.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;

import com.bambuser.broadcaster.BroadcastPlayer;
import com.bambuser.broadcaster.PlayerState;
import com.pbnj.pbnj.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by amgregoi on 7/21/18.
 */

public class PlayerActivity extends AppCompatActivity
{
    public final static String IRIS_API_KEY = "9w0vp35ztt1r9ouuys68si1bw";
    public final static String IRIS_APP_ID = "3fVmK6Dyr9OqSs9ClDeVMw";

    @BindView(R.id.PreviewSurfaceView) SurfaceView mSurfaceView;
    @BindView(R.id.textViewPlayerStatus) TextView mPlayerStatusTextView;

    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private BroadcastPlayer mBroadcastPlayer = null;
    private MediaController mMediaController = null;

    public static Intent newInstance(Context context)
    {
        Intent lIntent = new Intent(context, PlayerActivity.class);
        return lIntent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);

        initViews();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mPlayerStatusTextView.setText("Loading latest broadcast");
        getLatestResourceUri();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mBroadcastPlayer.close();
        mSurfaceView = null;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mBroadcastPlayer.close();
        mSurfaceView = null;
    }


    private void initViews()
    {
        mSurfaceView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mMediaController != null)
                {
                    if (mMediaController.isShowing())
                    {
                        mMediaController.hide();
                    }
                    else
                    {
                        mMediaController.show();
                    }
                }
            }
        });
    }

    private void getLatestResourceUri()
    {
        Request request = new Request.Builder()
                .url("https://api.irisplatform.io/broadcasts")
                .addHeader("Accept", "application/vnd.bambuser.v1+json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + IRIS_API_KEY)
                .get()
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(final Call call, final IOException e)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (mPlayerStatusTextView != null)
                        {
                            mPlayerStatusTextView.setText("Http exception: " + e);
                        }
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException
            {
                String body = response.body().string();
                String resourceUri = null;
                try
                {
                    JSONObject json = new JSONObject(body);
                    JSONArray results = json.getJSONArray("results");
                    JSONObject latestBroadcast = results.optJSONObject(0);
                    resourceUri = latestBroadcast.optString("resourceUri");
                }
                catch (Exception ignored)
                {
                }
                final String uri = resourceUri;
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        initPlayer(uri);
                    }
                });
            }
        });
    }

    private void initPlayer(String resourceUri)
    {
        if (resourceUri == null)
        {
            if (mPlayerStatusTextView != null)
            {
                mPlayerStatusTextView.setText("Could not get info about latest broadcast");
            }
            return;
        }

        if (mSurfaceView == null)
        {
            // UI no longer active
            return;
        }

        mBroadcastPlayer = new BroadcastPlayer(this, resourceUri, IRIS_APP_ID, mPlayerObserver);
        mBroadcastPlayer.setSurfaceView(mSurfaceView);
        mBroadcastPlayer.setAcceptType(BroadcastPlayer.AcceptType.ANY);
        mBroadcastPlayer.load();
    }

    /*********************************************************************************
     *
     *********************************************************************************/

    private final BroadcastPlayer.Observer mPlayerObserver = new BroadcastPlayer.Observer()
    {
        @Override
        public void onStateChange(PlayerState state)
        {
            if (mPlayerStatusTextView != null)
            {
                mPlayerStatusTextView.setText("Status: " + state);
            }
            if (state == PlayerState.PLAYING || state == PlayerState.PAUSED || state == PlayerState.COMPLETED)
            {
                if (mMediaController == null && mBroadcastPlayer != null && !mBroadcastPlayer.isTypeLive())
                {
                    mMediaController = new MediaController(PlayerActivity.this);
                    mMediaController.setAnchorView(mSurfaceView);
                    mMediaController.setMediaPlayer(mBroadcastPlayer);
                }
                if (mMediaController != null)
                {
                    mMediaController.setEnabled(true);
                    mMediaController.show();
                }
            }
            else if (state == PlayerState.ERROR || state == PlayerState.CLOSED)
            {
                if (mMediaController != null)
                {
                    mMediaController.setEnabled(false);
                    mMediaController.hide();
                }
                mMediaController = null;
            }
        }

        @Override
        public void onBroadcastLoaded(boolean live, int width, int height)
        {

        }
    };


}
