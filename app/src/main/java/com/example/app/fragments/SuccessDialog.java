package com.example.app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.R;

public class SuccessDialog extends DialogFragment {

    private String mSuccessMessage;
    private TextView mMessage;

    // Required empty public constructor
    public SuccessDialog() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param message Message to be displayed in the success dialog.
     * @return A new instance of fragment SuccessDialog.
     */
    public static SuccessDialog newInstance(String message) {
        SuccessDialog fragment = new SuccessDialog();
        Bundle args = new Bundle();
        args.putString("SuccessMessage", message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSuccessMessage = getArguments().getString("SuccessMessage");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_success_dialog, container, false);
        mMessage = view.findViewById(R.id.success_message);
        mMessage.setText(mSuccessMessage);
        return view;
    }

}
