package com.iddl.www.todoapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.twinkle94.monthyearpicker.picker.YearMonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button viewCalenderButton, viewAnalyticsButton;
    private String TAG = "MainActivity";
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCalendar = Calendar.getInstance();

        viewCalenderButton = findViewById(R.id.viewCalenderButton);
        viewAnalyticsButton = findViewById(R.id.viewAnalyticsButton);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Log.i(TAG, "Year : " + year + ", Month : " + monthOfYear + ", Day : " + dayOfMonth);
                Intent intent = new Intent(MainActivity.this, TodoListActivity.class);
                intent.putExtra("year", "" + year);
                intent.putExtra("month", "" + monthOfYear);
                intent.putExtra("day", "" + dayOfMonth);
                startActivity(intent);
            }

        };

        viewCalenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        viewAnalyticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YearMonthPickerDialog yearMonthPickerDialog = new YearMonthPickerDialog(MainActivity.this, new YearMonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onYearMonthSet(int year, int month) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
                        Log.i(TAG, "Year : " + year + ", Month : " + month);
                        Log.i(TAG, dateFormat.format(calendar.getTime()));

                        Intent intent = new Intent(MainActivity.this, AnalyticsActivity.class);
                        intent.putExtra("year", "" + year);
                        intent.putExtra("month", "" + month);
                        startActivity(intent);
                    }
                });
                yearMonthPickerDialog.show();
            }
        });
    }
}
