package com.pbnj.pbnj.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pbnj.pbnj.R;
import com.pbnj.pbnj.Util.SharedPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by amgregoi on 7/21/18.
 */

public class LoginActivity extends AppCompatActivity
{
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.editTextUsernameInput) EditText mUserNameInput;
    @BindView(R.id.buttonLoginFinished) Button mFinishedButton;

    public static Intent newInstance(Context context)
    {
        Intent lIntent = new Intent(context, LoginActivity.class);
        lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return lIntent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews()
    {
        Typeface lFontCircular = Typeface.createFromAsset(getAssets(), "fonts/CircularStd_Medium.otf");

        mUserNameInput.setTypeface(lFontCircular);
        mFinishedButton.setTypeface(lFontCircular);

        mUserNameInput.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (b)
                {
                    mUserNameInput.setHint(null);
                }
                else
                {
                    mUserNameInput.setHint("Name");
                }
            }
        });
    }

    @OnClick(R.id.buttonLoginFinished)
    public void onFinishedButtonClicked()
    {
        String lName = mUserNameInput.getText().toString();
        if (!lName.isEmpty())
        {
            SharedPrefs.setUserName(lName);
        }

        startActivity(MainActivity.newInstance(this));
    }

}
