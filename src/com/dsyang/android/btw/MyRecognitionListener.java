package com.dsyang.android.btw;

import android.content.Context;
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

    public MyRecognitionListener(Context c) {
        mContext = c;
        mStartRecordingPlayer = MediaPlayer.create(c, R.raw.start_record);
        mStopRecordingPlayer = MediaPlayer.create(c, R.raw.stop_record);

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
        Log.d("MainFragment", "RResults" + res.toString());
        TasksCollection.get(mContext).addTask(new Task(res.get(0)));
        Toast.makeText(mContext, res.get(0), Toast.LENGTH_LONG).show();

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
