package com.pbnj.pbnj.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.bambuser.broadcaster.BroadcastStatus;
import com.bambuser.broadcaster.Broadcaster;
import com.bambuser.broadcaster.CameraError;
import com.bambuser.broadcaster.ConnectionError;
import com.pbnj.pbnj.Util.PermissionManager;
import com.pbnj.pbnj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by amgregoi on 7/21/18.
 */

public class BroadcastActivity extends AppCompatActivity
{
    public final static String PROD_APP_KEY = "Z3h0YacmkUuLZNonJUbExg";
    public final static String SAND_APP_KEY = "3fVmK6Dyr9OqSs9ClDeVMw";
    @BindView(R.id.PreviewSurfaceView) SurfaceView mSurfaceView;

    private Broadcaster mBroadcaster;
    private boolean mBroadcastingFlag;

    public static Intent newInstance(Context context)
    {
        return new Intent(context, BroadcastActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        ButterKnife.bind(this);

        mBroadcaster = new Broadcaster(this, PROD_APP_KEY, mBroadcasterObserver);

        mBroadcaster.setRotation(getWindowManager().getDefaultDisplay().getRotation());
    }

    @Override
    public void onResume()
    {
        super.onResume();
        // Verify
        PermissionManager.getPermissions(this, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO);
        mBroadcaster.setCameraSurface(mSurfaceView);
        mBroadcaster.onActivityResume();
        mBroadcaster.setRotation(getWindowManager().getDefaultDisplay().getRotation());
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mBroadcaster.onActivityPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mBroadcaster.onActivityDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PermissionManager.PERMISSION_REQUEST:
                // handle declines
                break;
        }
    }

    @OnClick(R.id.buttonStartVideo)
    public void onStartClicked()
    {
        if (mBroadcastingFlag)
        {
            mBroadcaster.stopBroadcast();
            mBroadcastingFlag = false;
        }
        else
        {
            mBroadcaster.startBroadcast();
            mBroadcastingFlag = true;
        }
    }


    private Broadcaster.Observer mBroadcasterObserver = new Broadcaster.Observer()
    {
        @Override
        public void onConnectionStatusChange(BroadcastStatus broadcastStatus)
        {
            if (broadcastStatus == BroadcastStatus.STARTING)
            {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
            if (broadcastStatus == BroadcastStatus.IDLE)
            {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        }

        @Override
        public void onStreamHealthUpdate(int i)
        {
        }

        @Override
        public void onConnectionError(ConnectionError connectionError, String s)
        {
        }

        @Override
        public void onCameraError(CameraError cameraError)
        {
        }

        @Override
        public void onChatMessage(String s)
        {
        }

        @Override
        public void onResolutionsScanned()
        {
        }

        @Override
        public void onCameraPreviewStateChanged()
        {
        }

        @Override
        public void onBroadcastInfoAvailable(String s, String s1)
        {
        }

        @Override
        public void onBroadcastIdAvailable(String s)
        {
        }
    };

}
