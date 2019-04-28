package com.example.soundsynthesisturorial;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    public ProgressBar mProgressBar;

    public void showProgressBar(){
        if (mProgressBar == null){
            mProgressBar = new ProgressBar(this);
            mProgressBar.setIndeterminate(true);
        }
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        if (mProgressBar != null && mProgressBar.isAnimating()) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public void hideKeyboard(View view){
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    public void onStop(){
        super.onStop();
        hideProgressBar();
    }
}
