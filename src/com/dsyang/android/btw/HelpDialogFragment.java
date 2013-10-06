package com.dsyang.android.btw;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockDialogFragment;

/**
 * Created with IntelliJ IDEA.
 * User: dsyang
 * Date: 10/6/13
 * Time: 3:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class HelpDialogFragment extends SherlockDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setTitle(R.string.help_dialog_title)
                .setMessage(R.string.help_dialog_message)
                .setPositiveButton(R.string.hep_dialog_ok, null)
                .create();
    }
}
