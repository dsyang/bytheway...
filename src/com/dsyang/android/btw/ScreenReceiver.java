package com.dsyang.android.btw;

/**
 * Created with IntelliJ IDEA.
 * User: dsyang
 * Date: 10/6/13
 * Time: 1:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScreenReceiver {
    private static TasksDisplayer sTasksDisplayer;

    public static TasksDisplayer get() {
        if (sTasksDisplayer == null) {
            sTasksDisplayer = new TasksDisplayer();
        }

        return sTasksDisplayer;
    }
}
