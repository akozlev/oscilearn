package com.akozlev.oscilearn;

import android.animation.ArgbEvaluator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;

import java.util.ArrayList;
import java.util.List;

public class LessonPicker extends AppCompatActivity {
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_picker);

        models = new ArrayList<>();
        models.add(new Model(R.drawable.blacksynth, "Title 1", "Lorem ipsum dolore elesdkjfaslkdjf;alskj df ;lkasjd;flkjsd ;lfkjas;lkdfj a;lskdjf;lkasjd;flakj" ));
        models.add(new Model(R.drawable.colorfulmodular, "Title 2", "Lorem ipsum dolore elesdkjfaslkdjf;alskj df ;lkasjd;flkjsd ;lfkjas;lkdfj a;lskdjf;lkasjd;flakj" ));
        models.add(new Model(R.drawable.keyboardsynth, "Title 3", "Lorem ipsum dolore elesdkjfaslkdjf;alskj df ;lkasjd;flkjsd ;lfkjas;lkdfj a;lskdjf;lkasjd;flakj" ));

        for (Model model : models) {

        }
        adapter = new Adapter(models, this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,130,0);

        Integer[] colors_temp = {
                getColor(R.color.color1),
                getColor(R.color.color2),
                getColor(R.color.color3),
                getColor(R.color.color4),
        };

        colors = colors_temp;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (i < (adapter.getCount() - 1) && i < (colors.length - 1)) {
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    v,
                                    colors[i],
                                    colors[i + 1]
                            )
                    );
                } else {
                    viewPager.setBackgroundColor((colors[colors.length-1]));
                }

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
