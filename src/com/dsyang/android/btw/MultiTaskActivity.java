package com.dsyang.android.btw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import com.actionbarsherlock.app.SherlockActivity;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: dsyang
 * Date: 10/5/13
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class MultiTaskActivity extends SherlockActivity {

    private static Stack<Task> sTaskStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        sTaskStack = new Stack<Task>();
        sTaskStack.addAll(TasksCollection.get(this).getTasks());

        if(sTaskStack.isEmpty()) {
            finish();
        } else {
            Task t = sTaskStack.pop();
            showTask(t);
        }
    }

    public void showTask(Task t) {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra(MainFragment.EXTRA_TASK_TEXT, t.getText());
        intent.putExtra(MainFragment.EXTRA_TASK_DATE, t.getCreated());
        startActivityForResult(intent, MainFragment.RESULT_SINGLE_TASK);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);    //To change body of overridden methods use File | Settings | File Templates.

        switch (requestCode) {
            case MainFragment.RESULT_SINGLE_TASK:
                if(sTaskStack != null && !sTaskStack.empty()) {
                    showTask(sTaskStack.pop());
                } else {
                    finish();
                }
        }
    }

}
