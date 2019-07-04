package com.serving.servingpk;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public abstract class BaseActivity extends AppCompatActivity {
    public ProgressBar mProgressbar;
    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout constraintLayout=(ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base,null);
        FrameLayout frameLayout=constraintLayout.findViewById(R.id.activity_content);
        mProgressbar=constraintLayout.findViewById(R.id.progress_bar);

        getLayoutInflater().inflate(layoutResID,frameLayout,true);
        super.setContentView(constraintLayout);

    }

    public void showProgressBar(boolean visibility)
    {
        mProgressbar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }
}
