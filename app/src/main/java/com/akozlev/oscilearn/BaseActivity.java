package com.akozlev.oscilearn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import org.puredata.android.io.PdAudio;
import org.puredata.core.PdBase;

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
     @Override
    protected void onResume() {
        super.onResume();
        PdAudio.startAudio(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        PdAudio.stopAudio();
    }
}
