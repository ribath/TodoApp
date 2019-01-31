package com.iddl.www.todoapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdapterForTodoList extends RecyclerView.Adapter<AdapterForTodoList.ViewHolder>{

    private List<TodoTable> todoTableList;
    private DBHelper dbHelper;
    private Context context;
    private String TAG = "AdapterForTodoList";

    public AdapterForTodoList(Context context, DBHelper dbHelper, List<TodoTable> todoTableList) {
        this.context = context;
        this.dbHelper = dbHelper;
        this.todoTableList = todoTableList;
    }

    public void onDestroy() {
        for(int i=0; i<144; i++)
        {
            if(!todoTableList.get(i).getTitle().equals(""))
            {
                if(todoTableList.get(i).getFeed_back()==0)
                {
                    if(!ifAlreadyInDB(todoTableList.get(i)))
                    {
                        try
                        {
                            Dao<TodoTable, String> todoTableStringDao = dbHelper.getTodoTableDao();
                            todoTableStringDao.create(todoTableList.get(i));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_custom_row, parent, false);
//        AdapterForTodoList.ViewHolder vh = new AdapterForTodoList.ViewHolder(v);
        ViewHolder vh = new ViewHolder(v, new MyCustomEditTextListener());
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.custom_row_time.setText(todoTableList.get(holder.getAdapterPosition()).getTime());
        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition(), holder);
        holder.custom_row_task.setText(todoTableList.get(holder.getAdapterPosition()).getTitle());
        if(!todoTableList.get(holder.getAdapterPosition()).getTitle().equals(""))
        {
//            holder.custom_row_task.setText(todoTableList.get(holder.getAdapterPosition()).getTitle());
            if(todoTableList.get(holder.getAdapterPosition()).getFeed_back()==0)
            {
                holder.custom_row_layout_button.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.custom_row_layout_button.setVisibility(View.INVISIBLE);
                holder.custom_row_task.setFocusable(false);
            }
        }
        else
        {
            holder.custom_row_layout_button.setVisibility(View.INVISIBLE);
        }


        holder.positive_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todoTableList.get(holder.getAdapterPosition()).setFeed_back(1);
                notifyDataSetChanged();
                if(ifAlreadyInDB(todoTableList.get(position)))
                {
                    try {
                        Dao<TodoTable, String> todoTableStringDao = dbHelper.getTodoTableDao();
                        UpdateBuilder<TodoTable, String> updateBuilder = todoTableStringDao.updateBuilder();
                        Where where = updateBuilder.where();
                        where.eq("year", todoTableList.get(position).getYear());
                        where.and();
                        where.eq("month", todoTableList.get(position).getMonth());
                        where.and();
                        where.eq("day", todoTableList.get(position).getDay());
                        where.and();
                        where.eq("time", todoTableList.get(position).getTime());
                        updateBuilder.updateColumnValue("feed_back", 1);
                        updateBuilder.update();
                        holder.custom_row_layout_button.setVisibility(View.INVISIBLE);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    try
                    {
                        Dao<TodoTable, String> todoTableStringDao = dbHelper.getTodoTableDao();
                        todoTableStringDao.create(todoTableList.get(position));
                        holder.custom_row_layout_button.setVisibility(View.INVISIBLE);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        holder.negative_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todoTableList.get(holder.getAdapterPosition()).setFeed_back(-1);
                notifyDataSetChanged();
                if(ifAlreadyInDB(todoTableList.get(position)))
                {
                    try {
                        Dao<TodoTable, String> todoTableStringDao = dbHelper.getTodoTableDao();
                        UpdateBuilder<TodoTable, String> updateBuilder = todoTableStringDao.updateBuilder();
                        Where where = updateBuilder.where();
                        where.eq("year", todoTableList.get(position).getYear());
                        where.and();
                        where.eq("month", todoTableList.get(position).getMonth());
                        where.and();
                        where.eq("day", todoTableList.get(position).getDay());
                        where.and();
                        where.eq("time", todoTableList.get(position).getTime());
                        updateBuilder.updateColumnValue("feed_back", -1);
                        updateBuilder.update();
                        holder.custom_row_layout_button.setVisibility(View.INVISIBLE);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    try
                    {
                        Dao<TodoTable, String> todoTableStringDao = dbHelper.getTodoTableDao();
                        todoTableStringDao.create(todoTableList.get(position));
                        holder.custom_row_layout_button.setVisibility(View.INVISIBLE);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(todoTableList  != null) {
            return todoTableList.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public Button positive_button, negative_button;
        public TextView custom_row_time;
        public EditText custom_row_task;
        public LinearLayout custom_row_layout_button;
        public MyCustomEditTextListener myCustomEditTextListener;

        public ViewHolder(View itemView, MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);

            positive_button = itemView.findViewById(R.id.positive_button);
            negative_button = itemView.findViewById(R.id.negative_button);
            custom_row_time = itemView.findViewById(R.id.custom_row_time);
            custom_row_task = itemView.findViewById(R.id.custom_row_task);
            custom_row_layout_button = itemView.findViewById(R.id.custom_row_layout_button);

            this.myCustomEditTextListener = myCustomEditTextListener;
            this.custom_row_task.addTextChangedListener(myCustomEditTextListener);
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private ViewHolder viewHolder;

        public void updatePosition(int position, ViewHolder viewHolder) {
            this.position = position;
            this.viewHolder = viewHolder;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            Log.i(TAG, "position : "+position);
            todoTableList.set(position, new TodoTable(todoTableList.get(position).getYear(),
                    todoTableList.get(position).getMonth(),
                    todoTableList.get(position).getDay(),
                    todoTableList.get(position).getTime(),
                    charSequence.toString(),
                    todoTableList.get(position).getFeed_back()));
            if(charSequence.length()==0)
            {
                viewHolder.custom_row_layout_button.setVisibility(View.INVISIBLE);
            }
            else
            {
                viewHolder.custom_row_layout_button.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private boolean ifAlreadyInDB(TodoTable todoTable)
    {
        List<TodoTable> todoTableListTemp = new ArrayList<>();
        try {
            Dao<TodoTable, String> todoTableStringDao = dbHelper.getTodoTableDao();
            QueryBuilder<TodoTable, String> queryBuilder = todoTableStringDao.queryBuilder();
            Where where = queryBuilder.where();
            where.eq("year", todoTable.getYear());
            where.and();
            where.eq("month", todoTable.getMonth());
            where.and();
            where.eq("day", todoTable.getDay());
            where.and();
            where.eq("time", todoTable.getTime());
            todoTableListTemp = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(todoTableListTemp!=null)
        {
            if(todoTableListTemp.size()>=1)
            {
                return true;
            }
        }
        return false;
    }
}
