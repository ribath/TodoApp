package com.iddl.www.todoapp;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoListActivity extends AppCompatActivity {

    private RecyclerView recycler_view_todo_list;
    private AdapterForTodoList adapterForTodoList;
    private String year, month, day;
    private DBHelper dbHelper;
    private String TAG = "TodoListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        year = getIntent().getExtras().getString("year");
        month = getIntent().getExtras().getString("month");
        day = getIntent().getExtras().getString("day");

        Log.i(TAG, "Year : "+year+", Month : "+month+", Day : "+day);

        dbHelper = new DBHelper(this);
        recycler_view_todo_list = findViewById(R.id.recycler_view_todo_list);
        populateRecyclerView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapterForTodoList.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void populateRecyclerView()
    {
        List<TodoTable> todoTableList = prepareAdapterObject();
        adapterForTodoList = new AdapterForTodoList(this, dbHelper, todoTableList);
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view_todo_list.setLayoutManager(LayoutManager);
        recycler_view_todo_list.setItemAnimator(new DefaultItemAnimator());
        recycler_view_todo_list.setAdapter(adapterForTodoList);
    }

    private List<TodoTable> prepareAdapterObject()
    {
        List<TodoTable> todoTableList = new ArrayList<>();
        List<TodoTable> todoTableListTemp = new ArrayList<>();
        String j,x;
        for(int i=0; i<144; i++)
        {
            j = ""+i/6;
            x = ""+i%6+"0";
            try {
                Dao<TodoTable, String> todoTableStringDao = dbHelper.getTodoTableDao();
                QueryBuilder<TodoTable, String> queryBuilder = todoTableStringDao.queryBuilder();
                Where where = queryBuilder.where();
                where.eq("year", year);
                where.and();
                where.eq("month", month);
                where.and();
                where.eq("day", day);
                where.and();
                where.eq("time", j+":"+x);
                todoTableListTemp = queryBuilder.query();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(todoTableListTemp.size()==0)
            {
                todoTableList.add(new TodoTable(year,
                        month,
                        day,
                        j+":"+x,
                        "",
                        0));
            }
            else
            {
                todoTableList.add(new TodoTable(year,
                        month,
                        day,
                        j+":"+x,
                        todoTableListTemp.get(0).getTitle(),
                        todoTableListTemp.get(0).getFeed_back()));
            }
        }
        return todoTableList;
    }
}
