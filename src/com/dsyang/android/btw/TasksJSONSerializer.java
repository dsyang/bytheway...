package com.dsyang.android.btw;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: dsyang                pebble talk room 491
 * Date: 10/5/13
 * Time: 2:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class TasksJSONSerializer {
    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private String mFilename;

    public TasksJSONSerializer(Context context, String filename) {
        mContext = context;
        mFilename = filename;
    }

    public ArrayList<Task> readTasks() throws JSONException, IOException {
        ArrayList<Task> tasks = new ArrayList<Task>();
        BufferedReader reader = null;

        try {
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < array.length(); i++) {
                tasks.add(new Task(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not Found: " + mFilename);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return tasks;
    }

    public void writeTasks(ArrayList<Task> tasks) throws JSONException, IOException {
        JSONArray array = new JSONArray();

        for (Task t: tasks) {
            array.put(t.toJSON());
        }

        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
            Log.i(TAG, "Written: " + array.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
