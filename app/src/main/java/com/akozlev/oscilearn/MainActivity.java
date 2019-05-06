package com.akozlev.oscilearn;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import android.view.animation.Animation;

import org.puredata.android.io.AudioParameters;
import org.puredata.android.io.PdAudio;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.utils.IoUtils;

import java.io.File;
import java.io.IOException;

public class MainActivity extends BaseActivity {
    private PdUiDispatcher dispatcher;
    Button button;
    Animation frombottom;
    Animation outbottom;
    private void initPd() throws IOException{
        int sampleRAte = AudioParameters.suggestSampleRate();
        PdAudio.initAudio(sampleRAte,0,2,8,true);
        dispatcher = new PdUiDispatcher();
        PdBase.setReceiver(dispatcher);
    }
    private void initGui(){

        button = findViewById(R.id.btnBeginLearning);
        mProgressBar = findViewById(R.id.progressBar);

        outbottom = AnimationUtils.loadAnimation(this,R.anim.outbottom);
        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        button.setAnimation(frombottom);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.animate().translationY(200).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        setContentView(R.layout.activity_synth_lesson);
                    }
                });
            }
        });
        /*Switch onOffSwitch = findViewById(R.id.onOff);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("onOffSwitch",String.valueOf(isChecked));
                float val = (isChecked) ? 1.0f : 0.0f;
                PdBase.sendFloat("onOff",val);
            }
        });*/
    }
    private void loadPdPatch() throws IOException{
        File dir = getFilesDir();
        IoUtils.extractZipResource(getResources().openRawResource(R.raw.simplepatch),dir,true);
        File pdPatch = new File(dir, "simplepatch.pd");
        PdBase.openPatch(pdPatch.getAbsolutePath());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            initPd();
            loadPdPatch();
        } catch (IOException e){
            finish();
        }
        initGui();
    }

}
