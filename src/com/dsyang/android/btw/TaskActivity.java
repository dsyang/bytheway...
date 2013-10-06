package com.dsyang.android.btw;

import android.support.v4.app.Fragment;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dsyang
 * Date: 10/5/13
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        String text = getIntent().getStringExtra(MainFragment.EXTRA_TASK_TEXT);
        Date date = (Date) getIntent().getSerializableExtra(MainFragment.EXTRA_TASK_DATE);

        return TaskFragment.newInstance(text, date);
    }
}