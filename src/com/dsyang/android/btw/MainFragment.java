package com.dsyang.android.btw;

import android.content.Intent;
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
    private static final int RESULT_SINGLE_TASK = 2;
    private static final int RESULT_SPEECH = 1;
    private ImageButton mRecordButton;
    private EditText mMemoText;
    private TextView mIntroText;
    private SharedPreferences mPreferences;
    private TasksCollection mTasks;


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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mPreferences = getActivity().getSharedPreferences(getString(R.string.shard_pref_name), 0);
        mTasks = TasksCollection.get(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        /*mTasks.addTask(new Task("Call Mom!!"));
        mTasks.addTask(new Task("Take out the trash"));
        mTasks.addTask(new Task("Go to Office Hours"));*/
        if (mTasks.isEmpty()) {
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
                    return true;  //To change body of implemented methods use File | Settings | File Templates.
                }
            });

        } else {
            showAllTasks();
        }

        return v;
    }

    public void showAllTasks() {
        for (int i = 0; i < mTasks.size(); i++) {
            Intent intent = new Intent(getActivity(), TaskActivity.class);
            intent.putExtra(EXTRA_TASK_TEXT, mTasks.getTask(i).getText());
            intent.putExtra(EXTRA_TASK_DATE, mTasks.getTask(i).getCreated());
            if(i != mTasks.size()-1) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
            startActivityForResult(intent, RESULT_SINGLE_TASK);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if( resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<String> text =
                            data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mMemoText.setText(text.get(0));
                }

            }

        }
    }
}
