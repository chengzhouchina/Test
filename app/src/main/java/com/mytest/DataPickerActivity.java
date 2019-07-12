package com.mytest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.UriMatcher;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class DataPickerActivity extends AppCompatActivity {

    private Calendar calendar;
    private DatePicker datePicker;
    private TextView tv_test;
    private TimePicker timePicker;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_picker);
        //获取日历的一个对象
        year = calendar.get(Calendar.YEAR);
        calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH) + 1;//1月当0计算
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        tv_test = findViewById(R.id.tv_test);
        datePicker = findViewById(R.id.data_picker);
        timePicker = findViewById(R.id.time_picker);
        datePicker.init(year, calendar.get(Calendar.MONTH), day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tv_test.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        });

        tv_test.setText(year + "-" + month + "-" + day + " " + hour + "." + minute);

//        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//            }
//        },year,calendar.get(Calendar.MONTH),day).show();

        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            }
        },hour,minute,true).show();
    }
}
