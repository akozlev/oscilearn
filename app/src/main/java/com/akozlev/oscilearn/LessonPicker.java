package com.akozlev.oscilearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LessonPicker extends AppCompatActivity implements LessonAdapter.OnLessonListener {
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Lesson> lessonList;
    private static int LESSON_PICKER = 1;
    private static String TAG = "Lesson Picker";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_picker);
        recyclerView = findViewById(R.id.recyclerLessonView);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        lessonList = new ArrayList<>();
        lessonList.add(
                new Lesson(
                        "Sine Wave",
                        "The sine wave is a wave that is a pure tone. It does not have any harmonics. It moves smoothly from a value " +
                                "of 0 to 1 and then from 1 trough 0 to -1 and back to 0. Use the headphone button to hear the target sample",
                        false,
                        "sine.pd")
            );
        lessonList.add(
                new Lesson(
                        "Triangle Wave",
                        "The Triangle Wave is has more harmonics than a sine wave but it has only even harmonics - 0 , 2, 4, 8 ,etc.",
                        false,
                        "triangle.pd")
            );
        lessonList.add(
                new Lesson(
                        "Square Wave",
                        "The square wave is has the most harmonics of the three waves available. It is often used in subtractive synthesis",
                        false,
                        "square.pd")
            );
        mAdapter = new LessonAdapter(this, lessonList, this);
        recyclerView.setAdapter(mAdapter);
         
    }

    @Override
    public void onLessonClick(int position) {
        Log.d(TAG, "onLessonClick: " +  position+ " clicked.");
        Lesson lesson = lessonList.get(position);
        Intent intent = new Intent(this, SynthLesson.class);
        intent.putExtra("lessonNum", position);
        intent.putExtra("patchName", lesson.getPatchName());
        intent.putExtra("title", lesson.getTitle());
        intent.putExtra("desc", lesson.getDesc());
        startActivityForResult(intent, LESSON_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RESULT_OK)
        {
            int pos = data.getIntExtra("lessonNum", -1);
            lessonList.get(pos).setCompleted(true);
            mAdapter.notifyItemChanged(pos);
        }
    }

}
