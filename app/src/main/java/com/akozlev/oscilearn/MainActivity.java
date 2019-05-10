package com.akozlev.oscilearn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.view.animation.Animation;


public class MainActivity extends BaseActivity {
   TextView textView;
    Button button;
    Animation frombottom;
    Animation outbottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGui();
    }

    private void initGui(){

        button = findViewById(R.id.btnBeginLearning);
        textView = findViewById(R.id.title);
        textView.animate().alpha(0).setDuration(1000);
        textView.animate().alpha(1);

        outbottom = AnimationUtils.loadAnimation(this,R.anim.outbottom);
        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        button.setAnimation(frombottom);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.animate().translationY(300).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getBaseContext(), LessonPicker.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    }
}
