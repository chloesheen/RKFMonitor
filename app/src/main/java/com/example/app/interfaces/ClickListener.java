package com.example.app.interfaces;

import android.view.View;

public interface ClickListener {

    void onClick(View view, int position);

    void onLongPress(View view, int position);
}
