package fr.upem.android.deadhal.dialog;

import fr.upem.android.deadhal.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

public class SaveDialogFragment extends DialogFragment
{
    public interface SaveDialogListener {
        public void onDialogPositiveClick(SaveDialogFragment dialog);
        public void onDialogNegativeClick(SaveDialogFragment dialog);
    }

    private SaveDialogListener listener;
    private EditText levelName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        this.levelName                  = new EditText(this.getActivity());
        final AlertDialog.Builder alert = new AlertDialog.Builder(this.getActivity());

        
        alert
            .setTitle(R.string.builder_menu_save)
            .setMessage(R.string.builder_save_level_name)
            .setView(this.levelName)
            .setPositiveButton(R.string.builder_save_level_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    listener.onDialogPositiveClick(SaveDialogFragment.this);
                }
            })
            .setNegativeButton(R.string.builder_save_level_nok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    listener.onDialogNegativeClick(SaveDialogFragment.this);
                }
            });
        
        return alert.create();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
            this.listener = (SaveDialogListener) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SaveDialogListener");
        }
    }

    public String getLevelName()
    {
        return this.levelName.getText().toString();
    }
}
