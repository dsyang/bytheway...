package com.dsyang.android.btw;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dsyang
 * Date: 10/5/13
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskFragment extends SherlockFragment
        implements GestureDetector.OnGestureListener,
                   GestureDetector.OnDoubleTapListener {

    public  final String TAG = this.getClass().getSimpleName();

    private static final String EXTRA_TEXT = "com.dsyang.android.btw.task_text";
    private static final String EXTRA_DATE = "com.dsyang.android.btw.task_date";

    private TextView mText;
    private TextView mDateText;
    private Task mTask;
    private Button mDoneButton;
    private static GestureDetectorCompat sDetector;


    public static TaskFragment newInstance(String text, Date date) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        args.putSerializable(EXTRA_DATE, date);

        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        fragment.createTask();
        return fragment;
    }

    private void createTask() {
        mTask = new Task(getArguments().getString(EXTRA_TEXT));
        mTask.setCreated((Date) getArguments().getSerializable(EXTRA_DATE));
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
        setRetainInstance(true);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);

        sDetector = new GestureDetectorCompat(getActivity(),this);


        mText = (TextView) v.findViewById(R.id.single_task_text);
        mText.setText(mTask.getText());
        mText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return sDetector.onTouchEvent(event);
            }
        });

        mDateText = (TextView) v.findViewById(R.id.single_task_date);
        mDateText.setText(mTask.getCreated().toString());

        return v;
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float dy = e1.getY() - e2.getY();

        if(dy > 100) {
            Log.d(TAG, "swipeUp!");
            Toast.makeText(getActivity(), "Stored to some task software", Toast.LENGTH_SHORT).show();
            handleStorage();
        } else if (e1.getX() - e2.getX() > 100 && dy < 100 ) {
            Log.d(TAG, "swipeLeft!");
        } else if (e2.getX() - e1.getX() > 100 && dy < 100) {
            Log.d(TAG, "swipeRight!");
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(50);
        Log.d(TAG, "LongPress: did you actually finish?" + e.toString());
        Toast.makeText(getActivity(), "Show completed dialog", Toast.LENGTH_SHORT).show();
        handleCompletion();
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: Dismiss!" + e.toString());
        Toast.makeText(getActivity(), "I'll come back later", Toast.LENGTH_SHORT).show();
        handleDismiss();
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public void handleDismiss() {
        getActivity().finish();
    }

    public void handleCompletion() {
        SherlockDialogFragment dialog = new TaskCompleteDialog(mTask);
        dialog.show(getFragmentManager(), "completed");
    }

    public void handleStorage() {

    }




    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        //Log.d(TAG, "onSingleTapConfirmed: " + e.toString());
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //Log.d(TAG, "onScroll: " + e1.toString()+e2.toString());
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onDown(MotionEvent e) {
        //Log.d(TAG, "onDown: " + e.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //Log.d(TAG, "onShowPress: " + e.toString());
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }


}
