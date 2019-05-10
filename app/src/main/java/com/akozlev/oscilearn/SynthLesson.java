package com.akozlev.oscilearn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.puredata.android.io.AudioParameters;
import org.puredata.android.io.PdAudio;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.PdReceiver;
import org.puredata.core.utils.IoUtils;

import java.io.File;
import java.io.IOException;

import java.io.IOException;

import it.beppi.knoblibrary.Knob;

public class SynthLesson extends BaseActivity implements View.OnClickListener, Knob.OnStateChanged, SeekBar.OnSeekBarChangeListener, Spinner.OnItemSelectedListener{
    private PdUiDispatcher dispatcher;
    private final String TAG = "SynthLesson";
    private final int SYNTH_LESSON = 2;
    int lessonNum;
    TextView titleView;
    Knob master;
    Knob left;
    Knob right;
    SeekBar attack;
    SeekBar decay;
    SeekBar sustain;
    SeekBar release;
    Spinner dropdown;
    ImageButton audition;
    Button check;
    Button submit;
    boolean correctState = false;
    TextView descView;
    Bundle bundle;
    private String patchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synth_lesson);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        if (bundle == null) {
            Log.e(TAG, "onCreate: No Lesson" );
        } else {
            patchName = bundle.getString("patchName");
           lessonNum = bundle.getInt("lessonNum");
        }
        try {
            initPd();
            loadPdPatch(patchName);
        } catch (IOException e){
            finish();
        }
        initGui();
    }

    private void initGui() {

        titleView = findViewById(R.id.toolbar_title);
        descView = findViewById(R.id.txtDescription);
        dropdown = findViewById(R.id.spinOscillator);
        master = findViewById(R.id.knobMaster);
        left = findViewById(R.id.knobLeft);
        right = findViewById(R.id.knobRight);
        attack = findViewById(R.id.seekAttack);
        decay = findViewById(R.id.seekDecay);
        sustain = findViewById(R.id.seekSustain);
        release = findViewById(R.id.seekRelease);
        audition = findViewById(R.id.btnAudition);
        check = findViewById(R.id.btnCheckSolution);
        submit = findViewById(R.id.btnSubmit);
        descView.setText(bundle.getString("desc"));
        titleView.setText(bundle.getString("title"));

        PdBase.setReceiver(new PdReceiver() {
            @Override
            public void print(String s) {
                if (s.contains("correctState")) {
                    correctState=true;
                } else if (s.contains("incorrectState")) {
                    correctState=false;
                }
                Log.d(TAG, "pd:" + s);
            }

            @Override
            public void receiveBang(String source) {
                Log.d(TAG, "receiveBang: received bang" );
                if (source.equals("answer")) {
                    Log.d(TAG, "receiveBang: correct");
                }

            }

            @Override
            public void receiveFloat(String source, float x) {
                Log.d(TAG, "receiveFloat: <<-- " );
                if (source.equals("answer")) {
                    Log.d(TAG, "receiveFloat: correct" );
                }
            }

            @Override
            public void receiveSymbol(String source, String symbol) {
                Log.d(TAG, "receiveSymbol:"+ source +" content: " + symbol);
            }

            @Override
            public void receiveList(String source, Object... args) {

            }

            @Override
            public void receiveMessage(String source, String symbol, Object... args) {

            }
        });

        dropdown.setOnItemSelectedListener(this);
        audition.setOnClickListener(this);
        check.setOnClickListener(this);
        submit.setOnClickListener(this);
        attack.setOnSeekBarChangeListener(this);
        decay.setOnSeekBarChangeListener(this);
        sustain.setOnSeekBarChangeListener(this);
        release.setOnSeekBarChangeListener(this);

        master.setOnStateChanged(new Knob.OnStateChanged() {
            @Override
            public void onState(int i) {

            }
        });
        left.setOnStateChanged(new Knob.OnStateChanged() {
            @Override
            public void onState(int i) {

            }
        });
        right.setOnStateChanged(new Knob.OnStateChanged() {
            @Override
            public void onState(int i) {

            }
        });
    }

    private void onCheckClicked() {
        PdBase.sendBang("check");

        if (correctState) {
            Log.d(TAG, "onCheckClicked: correctState");
            Intent intent = getIntent();
            intent.putExtra("lessonNum",lessonNum);
            intent.putExtra("completed",true);
            setResult(RESULT_OK, intent);

        }
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PdBase.pauseAudio();
        finish();
    }

    private void initPd() throws IOException {
        int sampleRAte = AudioParameters.suggestSampleRate();
        PdAudio.initAudio(sampleRAte,0,2,8,true);
        dispatcher = new PdUiDispatcher();
        PdBase.setReceiver(dispatcher);
    }

    private void loadPdPatch(String patchName) throws IOException{
        File dir = getFilesDir();
        IoUtils.extractZipResource(getResources().openRawResource(R.raw.patches),dir,true);
        File pdPatch = new File(dir, patchName);
        PdBase.openPatch(pdPatch.getAbsolutePath());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAudition:
                Log.d(TAG, "onClick: audition");
                PdBase.sendBang("audition");
                break;
            case R.id.btnCheckSolution:
                PdBase.sendBang("start");
                break;
            case R.id.btnSubmit:
                onCheckClicked();
            default:
                break;
        }


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
             case R.id.seekAttack:
                PdBase.sendFloat("attack",(float)i);
                break;
            case R.id.seekDecay:
                PdBase.sendFloat("decay",(float)i);
                break;
            case R.id.seekSustain:
                PdBase.sendFloat("sustain",(float)i);
                break;
            case R.id.seekRelease:
                PdBase.sendFloat("release",(float)i);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onState(int i) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        PdBase.sendFloat("osci", (float)i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
