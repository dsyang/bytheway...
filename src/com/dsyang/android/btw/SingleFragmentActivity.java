package com.dsyang.android.btw;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.dsyang.android.btw.R;

public abstract class SingleFragmentActivity extends SherlockFragmentActivity {

    protected abstract Fragment createFragment();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Sherlock);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (savedInstanceState == null && fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

    }
}
