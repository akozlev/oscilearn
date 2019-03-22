package com.example.soundsynthesisturorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import org.puredata.android.io.AudioParameters;
import org.puredata.android.io.PdAudio;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.utils.IoUtils;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private PdUiDispatcher dispatcher;
    private void initPd() throws IOException{
        int sampleRAte = AudioParameters.suggestSampleRate();
        PdAudio.initAudio(sampleRAte,0,2,8,true);

        dispatcher = new PdUiDispatcher();
        PdBase.setReceiver(dispatcher);
    }
    private void initGui(){
        Switch onOffSwitch = (Switch) findViewById(R.id.onOff);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("onOffSwitch",String.valueOf(isChecked));
                float val = (isChecked) ? 1.0f : 0.0f;
                PdBase.sendFloat("onOff",val);
            }
        });
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
