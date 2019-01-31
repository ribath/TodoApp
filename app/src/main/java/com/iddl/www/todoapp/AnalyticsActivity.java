package com.iddl.www.todoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnalyticsActivity extends AppCompatActivity {

    private AdapterForAnalytics adapterForAnalytics;
    private RecyclerView recycler_view_analytics;
    private TextView textView_body_positive, textView_body_negative, textView_body_undecided, texView_emptyList;
    private DBHelper dbHelper;
    private String year, month;
    private String TAG = "AnalyticsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        year = getIntent().getExtras().getString("year");
        month = getIntent().getExtras().getString("month");

        dbHelper = new DBHelper(this);
        recycler_view_analytics = findViewById(R.id.recycler_view_analytics);
        textView_body_positive = findViewById(R.id.textView_body_positive);
        textView_body_negative = findViewById(R.id.textView_body_negative);
        textView_body_undecided = findViewById(R.id.textView_body_undecided);
        texView_emptyList = findViewById(R.id.texView_emptyList);
        populateRecyclerView();
    }

    private void populateRecyclerView()
    {
        List<TodoTable> todoTableList = prepareAdapterObject();
        adapterForAnalytics = new AdapterForAnalytics(todoTableList, this, year, month, texView_emptyList);
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view_analytics.setLayoutManager(LayoutManager);
        recycler_view_analytics.setItemAnimator(new DefaultItemAnimator());
        recycler_view_analytics.setAdapter(adapterForAnalytics);
    }

    private List<TodoTable> prepareAdapterObject()
    {
        List<TodoTable> todoTableListTemp = new ArrayList<>();
        try {
            Dao<TodoTable, String> todoTableStringDao = dbHelper.getTodoTableDao();
            QueryBuilder<TodoTable, String> queryBuilder = todoTableStringDao.queryBuilder();
            Where where = queryBuilder.where();
            where.eq("year", year);
            where.and();
            where.eq("month", month);
            todoTableListTemp = queryBuilder.query();
            prepareFooter(todoTableListTemp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todoTableListTemp;
    }

    private void prepareFooter(List<TodoTable> todoTables)
    {
        double p=0, n=0, u=0;
        for(int i=0; i<todoTables.size(); i++)
        {
            if(todoTables.get(i).getFeed_back()==1)
            {
                p++;
            }
            else if(todoTables.get(i).getFeed_back()==-1)
            {
                n++;
            }
            else
            {
                u++;
            }

            textView_body_positive.setText(String.format("%.2f", (p/todoTables.size())*100)+"%");
            textView_body_negative.setText(String.format("%.2f", (n/todoTables.size())*100)+"%");
            textView_body_undecided.setText(String.format("%.2f", (u/todoTables.size())*100)+"%");
        }
    }
}
