package com.example.app.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class StudentListClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector mGestureDetector;
    private ClickListener mClickListener;

    public StudentListClickListener (Context context, final RecyclerView recyclerView, final ClickListener listener) {
        mClickListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) { return true; }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if (child != null && listener != null) {
                    listener.onLongPress(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());

        if (child != null && mClickListener != null && mGestureDetector.onTouchEvent(e)) {

            mClickListener.onClick(child, rv.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    public interface ClickListener {

        void onClick(View view, int position);

        void onLongPress(View view, int position);
    }
}
