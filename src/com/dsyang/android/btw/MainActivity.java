package com.dsyang.android.btw;

import android.support.v4.app.Fragment;

/**
 * Created with IntelliJ IDEA.
 * User: dsyang
 * Date: 10/5/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */

public class MainActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return MainFragment.newInstance();  //To change body of implemented methods use File | Settings | File Templates.
    }
}