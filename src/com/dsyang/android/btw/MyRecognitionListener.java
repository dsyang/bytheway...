package com.dsyang.android.btw;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: dsyang
 * Date: 10/5/13
 * Time: 8:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyRecognitionListener implements RecognitionListener {
    private final String TAG = this.getClass().getSimpleName();

    private MediaPlayer mStartRecordingPlayer;
    private MediaPlayer mStopRecordingPlayer;
    private Context mContext;
    private SharedPreferences mPreferences;

    public MyRecognitionListener(Context c) {
        mContext = c;
        mStartRecordingPlayer = MediaPlayer.create(c, R.raw.start_record);
        mStopRecordingPlayer = MediaPlayer.create(c, R.raw.stop_record);

        mPreferences = c.getApplicationContext().getSharedPreferences(c.getString(R.string.shard_pref_name),0);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
//        mStartRecordingPlayer.start();
        Log.d("MainFragment", "Start Recording!");
    }

    @Override
    public void onBeginningOfSpeech() {
//        Log.d("MainFragment", "On Beginning!");
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onRmsChanged(float rmsdB) {
//        Log.d("MainFragment", "On RMS!");
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
//        Log.d("MainFragment", "On Buffered Received!");
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onEndOfSpeech() {
        mStopRecordingPlayer.start();
        Log.d("MainFragment", "Stop Recording!");
    }

    @Override
    public void onError(int error) {
        Log.d("MainFragment", "ERROR!" + error);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> res = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        TasksCollection.get(mContext).addTask(new Task(res.get(0)));
        Toast.makeText(mContext, res.get(0), Toast.LENGTH_SHORT).show();
        boolean receiverOn = mPreferences.contains(MainFragment.PREF_IS_RECEIVER_ACTIVE)
                && mPreferences.getBoolean(MainFragment.PREF_IS_RECEIVER_ACTIVE, true);
        Log.d("MainFragment", "RResults" + res.toString() + receiverOn);
        if (!receiverOn) {
            startReceiver();
        }
    }

    private void startReceiver() {
        mPreferences.edit().putBoolean(MainFragment.PREF_IS_RECEIVER_ACTIVE, true).commit();
        Log.d(TAG, "Starting BR from recognition listener "
                + mPreferences.getBoolean(MainFragment.PREF_IS_RECEIVER_ACTIVE, false));
        IntentFilter intentf = new IntentFilter(Intent.ACTION_SCREEN_ON);
        mContext.getApplicationContext().registerReceiver(ScreenReceiver.get(), intentf);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
