package com.example.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.util.FoodRatios;


public class LunchProgramDialog extends DialogFragment implements View.OnClickListener{

    private TextView mGitheri;
    private TextView mRiceBeans;
    private OnLunchSelectionListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View dialog = inflater.inflate(R.layout.fragment_lunch_program_dialog, container, false);

        mGitheri = dialog.findViewById(R.id.githeri);
        mGitheri.setOnClickListener(this);

        mRiceBeans = dialog.findViewById(R.id.rice_beans);
        mRiceBeans.setOnClickListener(this);

        return dialog;
    }

    @Override
    public void onClick(View view) {
        mListener.onLunchSelected(view);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLunchSelectionListener) {
            mListener = (OnLunchSelectionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnLunchSelectionListener {


        void onLunchSelected(View view);
    }
}
