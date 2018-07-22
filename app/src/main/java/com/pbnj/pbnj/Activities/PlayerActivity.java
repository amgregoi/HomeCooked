package com.pbnj.pbnj.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bambuser.broadcaster.BroadcastPlayer;
import com.bambuser.broadcaster.PlayerState;
import com.pbnj.pbnj.Adapter.MessageAdapter;
import com.pbnj.pbnj.Fragments.RecipeFragment;
import com.pbnj.pbnj.Models.Message;
import com.pbnj.pbnj.R;
import com.pbnj.pbnj.Util.KeyboardUtil;
import com.pbnj.pbnj.Util.SharedPrefs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    //General Views
    @BindView(R.id.progressBarPlayer) ProgressBar mProgressBar;
    @BindView(R.id.cardViewLiveStatus) CardView mLiveStatusContainer;
    @BindView(R.id.textViewWatchingCount) TextView mWatchCount;

    //Player Views
    @BindView(R.id.PreviewSurfaceView) SurfaceView mSurfaceView;
    @BindView(R.id.textViewPlayerStatus) TextView mPlayerStatusTextView;

    //Chat views
    @BindView(R.id.editTextMessageEntryInput) EditText mMessageEntry;
    @BindView(R.id.recyclerViewPlayerMessages) RecyclerView mRecyclerViewMessages;


    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private BroadcastPlayer mBroadcastPlayer = null;
    private MediaController mMediaController = null;

    private MessageAdapter mMessageAdapter;
    private RecyclerView.LayoutManager mManager;

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
        showProgressBar();
        mPlayerStatusTextView.setText("Loading latest broadcast");
        getLatestResourceUri();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mBroadcastPlayer.close();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mBroadcastPlayer.close();
    }


    private void initViews()
    {
        Typeface lFontCircular = Typeface.createFromAsset(getAssets(), "fonts/CircularStd_Book.otf");
        mMessageEntry.setTypeface(lFontCircular);

        // recyclerview
        mMessageAdapter = new MessageAdapter();
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewMessages.setAdapter(mMessageAdapter);
        mRecyclerViewMessages.setLayoutManager(mManager);

        mMessageEntry.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                // Send message to api
                // add message to adapter
                String lMessage = mMessageEntry.getText().toString();

                if (lMessage.isEmpty())
                {
                    return false;
                }

                mMessageAdapter.addMessage(new Message(SharedPrefs.getUserName(), lMessage));
                mMessageEntry.setText("");
                mMessageEntry.clearFocus();
                KeyboardUtil.hide(PlayerActivity.this);
                mRecyclerViewMessages.smoothScrollToPosition(mMessageAdapter.getItemCount() - 1);

                return true;
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
        mBroadcastPlayer.setAcceptType(BroadcastPlayer.AcceptType.ANY); //ANY
        mBroadcastPlayer.setViewerCountObserver(mViewerCountObserver);
        mBroadcastPlayer.load();

        if (mBroadcastPlayer.isTypeLive())
        {
            mLiveStatusContainer.setVisibility(View.VISIBLE);
            mBroadcastPlayer.seekTo(mBroadcastPlayer.getDuration());
        }
        else
        {
            mLiveStatusContainer.setVisibility(View.VISIBLE);
        }
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
                hideProgressBar();
                if (mMediaController == null && mBroadcastPlayer != null && !mBroadcastPlayer.isTypeLive())
                {
                    mMediaController = new MediaController(PlayerActivity.this);
                    mMediaController.setAnchorView(mSurfaceView);
                    mMediaController.setMediaPlayer(mBroadcastPlayer);
                }
                if (mMediaController != null)
                {
                    mMediaController.setEnabled(true);
                }
            }
            else if (state == PlayerState.ERROR || state == PlayerState.CLOSED)
            {
                hideProgressBar();

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

    private final BroadcastPlayer.ViewerCountObserver mViewerCountObserver = new BroadcastPlayer.ViewerCountObserver()
    {
        @Override
        public void onCurrentViewersUpdated(long viewers)
        {
            if (mWatchCount != null)
            {
                mWatchCount.setText(String.format(Locale.getDefault(), "%d", viewers));
            }
        }

        @Override
        public void onTotalViewersUpdated(long viewers)
        {
        }
    };

    private void showProgressBar()
    {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar()
    {
        mProgressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.cardViewRecipeStepContainer)
    public void onRecipeCardClicked()
    {
        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.frameLayoutRecipeContainer, RecipeFragment.newInstance(), RecipeFragment.TAG)
                                   .addToBackStack(RecipeFragment.TAG)
                                   .commit();
    }
}
