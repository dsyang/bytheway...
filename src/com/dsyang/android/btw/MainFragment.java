package com.dsyang.android.btw;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import com.dsyang.android.btw.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: dsyang
 * Date: 10/5/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */

public class MainFragment extends SherlockFragment {

    public final String TAG = this.getClass().getSimpleName();
    public static final String EXTRA_TASK_TEXT = "com.dsyang.android.btw.extra_task_text";
    public static final String EXTRA_TASK_DATE = "com.dsyang.android.btw.extra_task_date";
    public static final String PREF_IS_RECEIVER_ACTIVE = "com.dsyang.android.btw.preference.receiver_active";
    public static final int RESULT_SINGLE_TASK = 2;
    public static final int RESULT_SPEECH = 1;

    private ImageButton mRecordButton;
    private EditText mMemoText;
    private TextView mIntroText;
    private SharedPreferences mPreferences;
    private TasksCollection mTasks;
    private Stack<Task> mTaskStack;

    public BroadcastReceiver mTasksDisplayer;
    private static SpeechRecognizer sSpeechRecognizer;


    public static MainFragment newInstance() {
        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_register:
                startBroadcastReceiver();
                return true;
            case R.id.menu_unregister:
                stopBroadcastReceiver();
                return true;
            case R.id.menu_reset:
                mPreferences.edit().clear().commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        mPreferences = getActivity().getSharedPreferences(getString(R.string.shard_pref_name), 0);
        mTasks = TasksCollection.get(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mIntroText = (TextView) v.findViewById(R.id.intro_text);
        mMemoText = (EditText) v.findViewById(R.id.memo_text);
        mRecordButton = (ImageButton) v.findViewById(R.id.record_button);

        sSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());
        sSpeechRecognizer.setRecognitionListener(new MyRecognitionListener(getActivity()));

        mRecordButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                Intent rec = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                rec.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                rec.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE ,"com.dsyang.android.btw");
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        sSpeechRecognizer.startListening(rec);
                        break;
                    case MotionEvent.ACTION_UP:
                        sSpeechRecognizer.stopListening();
                        break;
                }
                return true;
            }
        });

        if (!mTasks.isEmpty()) {
            startActivity(new Intent(getActivity(), MultiTaskActivity.class));
            startBroadcastReceiver();
        } else {
            stopBroadcastReceiver();
        }
        return v;
    }


    public void startBroadcastReceiver() {
        if (mTasksDisplayer == null) {
            mTasksDisplayer = ScreenReceiver.get();
        }

        boolean receiverOn = mPreferences.contains(PREF_IS_RECEIVER_ACTIVE)
                          && mPreferences.getBoolean(PREF_IS_RECEIVER_ACTIVE, true);
        IntentFilter intentf = new IntentFilter(Intent.ACTION_SCREEN_ON);

        if (!receiverOn) {
            mPreferences.edit().putBoolean(PREF_IS_RECEIVER_ACTIVE, true).commit();
            Log.d(TAG, "Started BR");
            getActivity().registerReceiver(mTasksDisplayer, intentf);
        } else {
            Log.d(TAG, "Receiver already started.");
        }
    }


    public void stopBroadcastReceiver() {
        if (mTasksDisplayer != null) {
            try {
                getActivity().unregisterReceiver(mTasksDisplayer);
            } catch (IllegalArgumentException e) {
                mPreferences.edit().putBoolean(PREF_IS_RECEIVER_ACTIVE, false).commit();
                Log.d(TAG, "BR not registered");
            }
        }
        Log.d(TAG, "Stopped BR");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

}
