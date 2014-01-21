package com.virtualpm.main.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import com.virtualpm.main.R;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Joseph Altmaier
 * Date: 11/17/13
 * Time: 6:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalendarDialog extends DialogFragment implements CalendarView.OnDateChangeListener {
    public static final String TAG = "CalendarDialog";
    private long startDate;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.calendar_layout, container, false);
        CalendarView cv = (CalendarView)v.findViewById(R.id.calendarView);
        cv.setMaxDate(new Date().getTime());
        cv.setMinDate(0);
        cv.setOnDateChangeListener(this);
        startDate = cv.getDate();
        return v;
    }

    @Override
    public void onSelectedDayChange(CalendarView calendarView, int i, int i2, int i3) {
        if(startDate == calendarView.getDate())
            return;
        Date d = new Date();
        d.setTime(calendarView.getDate());
        Intent intent = getActivity().getIntent();
        intent.putExtra(TAG, d);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        getFragmentManager().popBackStack();
    }
}
