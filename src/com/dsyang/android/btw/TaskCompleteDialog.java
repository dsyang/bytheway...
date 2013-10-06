package com.dsyang.android.btw;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockDialogFragment;
import org.json.JSONException;

/**
 * Created with IntelliJ IDEA.
 * User: dsyang
 * Date: 10/5/13
 * Time: 6:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskCompleteDialog extends SherlockDialogFragment {
    private final String TAG = this.getClass().getSimpleName();
    private Task mTask;

    public TaskCompleteDialog(Task t) {
        mTask = t;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle(R.string.completed_dialog_title)
                .setMessage(R.string.completed_dialog_message)
                .setPositiveButton(R.string.completed_dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Complete!", Toast.LENGTH_SHORT).show();
                        TasksCollection.get(getActivity()).deleteTask(mTask);
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.completed_dialog_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT);
                    }
                }).create();

    }
}
