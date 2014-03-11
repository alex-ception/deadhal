package fr.upem.android.deadhal.dialog;

import java.io.FileNotFoundException;
import java.io.IOException;

import fr.upem.android.deadhal.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class LoadDialogFragment extends DialogFragment
{
    public interface LoadDialogListener
    {
        public void onDialogPositiveClick(LoadDialogFragment dialog, String levelName) throws FileNotFoundException, IOException;
        public void onDialogNegativeClick(LoadDialogFragment dialog);
    }

    private String[] files;
    private LoadDialogListener listener;

    /**
     * {@inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this.getActivity());
        this.files = this.getActivity().getApplicationContext().fileList();

        alert
            .setTitle(R.string.builder_menu_load)
            .setItems(this.files, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    try {
                        listener.onDialogPositiveClick(LoadDialogFragment.this, files[which]);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            })
        ;
        
        return alert.create();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
            this.listener = (LoadDialogListener) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement LoadDialogListener");
        }
    }
}
