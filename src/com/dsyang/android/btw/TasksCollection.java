package com.dsyang.android.btw;

import android.content.Context;
import android.util.Log;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: dsyang
 * Date: 10/5/13
 * Time: 1:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class TasksCollection {
    private final String TAG = this.getClass().getSimpleName();
    private static final String FILENAME = "tasks.json";

    private ArrayList<Task> mTasks;
    private static TasksCollection sTasksCollection;

    private Context mContext;
    private TasksJSONSerializer mSerializer;

    private TasksCollection(Context appContext) {
        mContext = appContext;
        mSerializer = new TasksJSONSerializer(mContext, FILENAME);
        mTasks = readFromDisk();
    }

    public static TasksCollection get(Context c) {
        if (sTasksCollection == null) {
            sTasksCollection = new TasksCollection(c);
        }
        return sTasksCollection;
    }

    public int size() {
        return mTasks.size();
    }

    public boolean isEmpty() {
        return mTasks.isEmpty();
    }

    public void addTask(Task t) {
        mTasks.add(t);
        writeToDisk();
    }

    public ArrayList<Task> getTasks() {
        return mTasks;
    }

    public Task getTask(int idx) {
        if(0 > idx || idx >= mTasks.size()){
            return new Task("Example, Call Home");
        }
        return mTasks.get(idx);
    }

    public void deleteTask(Task t) {
        int idx = -1;
        for (int i = 0; i < mTasks.size(); i++) {
            Task tt = mTasks.get(i);
            if(tt.getCreated().equals(t.getCreated()) && tt.getText().equals(t.getText())) {
                idx = i;
                break;
            }
        }
        if (idx != -1) {
            mTasks.remove(idx);
            writeToDisk();
        } else {
            Log.e(TAG, "Cannot delete task: " + t.getText());
        }

    }
    public ArrayList<Task> readFromDisk() {
        ArrayList<Task> tasks = null;
        try {
            tasks = mSerializer.readTasks();
            Log.i(TAG, "Tasks loaded. Found " + tasks.size());
        } catch (Exception e) {
            Log.e(TAG, "Cannot load saved tasks");
        }
        return tasks;
    }

    public boolean writeToDisk() {
        try {
            mSerializer.writeTasks(mTasks);
            Log.i(TAG, "Tasks Written!");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e + " caught");
            return false;
        }
    }
}