package com.iddl.www.todoapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterForAnalytics extends RecyclerView.Adapter<AdapterForAnalytics.ViewHolder>{

    private List<TodoTable> todoTableList;
    private Context context;
    private String year, month;
    private TextView texView_emptyList;
    private String TAG = "AdapterForAnalytics";

    public AdapterForAnalytics(List<TodoTable> todoTableList, Context context, String year, String month, TextView texView_emptyList) {
        this.todoTableList = todoTableList;
        this.context = context;
        this.year = year;
        this.month = month;
        this.texView_emptyList = texView_emptyList;

        if(todoTableList.size()==0)
        {
            texView_emptyList.setVisibility(View.VISIBLE);
        }
        else
        {
            texView_emptyList.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.analytics_custom_row, viewGroup, false);
        AdapterForAnalytics.ViewHolder vh = new AdapterForAnalytics.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterForAnalytics.ViewHolder viewHolder, int i) {
        TodoTable todoTable = todoTableList.get(i);

        viewHolder.custom_row_time.setText(todoTable.getDay()+"-"+month+1+"-"+year+", "+todoTable.getTime());
        viewHolder.custom_row_task.setText(todoTable.getTitle());
        if(todoTable.getFeed_back()==1)
        {
            viewHolder.imageVew_custom_row.setImageResource(R.drawable.ic_check_circle_black_24dp);
        }
        else if(todoTable.getFeed_back()==-1)
        {
            viewHolder.imageVew_custom_row.setImageResource(R.drawable.ic_highlight_off_black_24dp);
        }
        else
        {
            viewHolder.imageVew_custom_row.setImageResource(R.drawable.ic_hourglass_empty_black_24dp
            );
        }
    }


    @Override
    public int getItemCount() {
        if(todoTableList  != null) {
            return todoTableList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView custom_row_time, custom_row_task;
        public ImageView imageVew_custom_row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            custom_row_time = itemView.findViewById(R.id.custom_row_time);
            custom_row_task = itemView.findViewById(R.id.custom_row_task);
            imageVew_custom_row = itemView.findViewById(R.id.imageVew_custom_row);
        }
    }
}
