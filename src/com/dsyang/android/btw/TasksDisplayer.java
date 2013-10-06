package com.dsyang.android.btw;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: dsyang
 * Date: 10/5/13
 * Time: 10:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class TasksDisplayer extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Context appContext = context.getApplicationContext();
        Log.d("MainFragment", "Received action: " + action);
        if(action.equals(Intent.ACTION_SCREEN_ON) && !TasksCollection.get(context).isEmpty()) {
            Log.d("MainFragment", "show tasks");
            Intent i = new Intent(appContext, MultiTaskActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            appContext.startActivity(i);
        }

    }
}
