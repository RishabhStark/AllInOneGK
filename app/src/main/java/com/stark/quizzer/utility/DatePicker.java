package com.stark.quizzer.utility;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import com.stark.quizzer.Fragments.CurrentAffairs;
import com.stark.quizzer.R;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DatePicker extends DialogFragment{


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c=Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        CurrentAffairs ca=(CurrentAffairs) getActivity().getSupportFragmentManager().findFragmentByTag("FTAG");
        return new DatePickerDialog(getActivity(), R.style.Theme_AppCompat_Light,(DatePickerDialog.OnDateSetListener)ca,year,month,day);
    }


}
