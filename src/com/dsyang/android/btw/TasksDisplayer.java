package com.dsyang.android.btw;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: dsyang
 * Date: 10/5/13
 * Time: 10:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class TasksDisplayer extends BroadcastReceiver {
    private static Stack<Task> sTaskStack;



    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action.equals(Intent.ACTION_SCREEN_ON)) {


        }

    }
}
