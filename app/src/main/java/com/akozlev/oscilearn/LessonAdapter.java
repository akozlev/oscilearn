package com.akozlev.oscilearn;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {
    private Context mCtx;
    private List<Lesson> lessons;
    private OnLessonListener mOnLessonListener;

    public static class LessonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title,lessonNum;
        ImageView completed;
        OnLessonListener onLessonListener;

        public LessonViewHolder(@NonNull View itemView, OnLessonListener onLessonListener) {
            super(itemView);

            this.title = itemView.findViewById(R.id.txtLessonName);
            this.lessonNum = itemView.findViewById(R.id.txtLessonNumber);
            this.completed = itemView.findViewById(R.id.imgCompleted);
            this.onLessonListener = onLessonListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onLessonListener.onLessonClick(getAdapterPosition());
        }
    }

    public LessonAdapter(Context mCtx, List<Lesson> lessons, OnLessonListener onLessonListener) {
        this.mCtx = mCtx;
        this.lessons = lessons;
        this.mOnLessonListener = onLessonListener;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lesson, viewGroup, false);

        LessonViewHolder vh = new LessonViewHolder(view, mOnLessonListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder lessonViewHolder, int i) {
        Lesson lesson = lessons.get(i);
        lessonViewHolder.title.setText(lesson.getTitle());
        lessonViewHolder.lessonNum.setText(String.format(String.valueOf(i+1), '.'));
        if (lesson.isCompleted()) {
            lessonViewHolder.completed.setColorFilter(R.color.colorCompleted);
            lessonViewHolder.completed.setImageResource(R.drawable.ic_star);
        } else {
            lessonViewHolder.completed.setColorFilter(R.color.colorNotCompleted);
            lessonViewHolder.completed.setImageResource(R.drawable.ic_star_border);
        }

    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public interface OnLessonListener {
        void onLessonClick(int position);
    }

}
