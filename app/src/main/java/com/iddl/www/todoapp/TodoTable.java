package com.iddl.www.todoapp;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "todo_table")
public class TodoTable implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField()
    private String year;

    @DatabaseField()
    private String month;

    @DatabaseField()
    private String day;

    @DatabaseField()
    private String time;

    @DatabaseField()
    private String title;

    @DatabaseField()
    private int feed_back;

    public TodoTable() {
    }

    public TodoTable(String year, String month, String day, String time, String title, int feed_back) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.time = time;
        this.title = title;
        this.feed_back = feed_back;
    }

    public int getId() {
        return id;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public int getFeed_back() {
        return feed_back;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFeed_back(int feed_back) {
        this.feed_back = feed_back;
    }
}
