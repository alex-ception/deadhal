package fr.upem.android.deadhal.dialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import fr.upem.android.deadhal.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;

/**
 * A class representing a dialog for loading a maze
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class LoadDialogFragment extends DialogFragment
{
    /**
     * An interface representing the contract to follow for the listener
     */
    public interface LoadDialogListener
    {
        public void onDialogPositiveClick(LoadDialogFragment dialog, String levelName) throws FileNotFoundException, IOException;
        public void onDialogNegativeClick(LoadDialogFragment dialog);
    }

    private LoadDialogListener listener;

    public static String[] arrayRemove(String[] array, String toRemove)
    {
        List<String> result = new LinkedList<String>();
        
        for (String item : array)
            if (!toRemove.equals(item))
                result.add(item);

        return result.toArray(array);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this.getActivity());
        File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        FilenameFilter fnf = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name)
            {
                if (name.endsWith(".xml"))
                    return true;
                return false;
            }
        };
        final String[] files = sdcard.list(fnf);

        alert
            .setTitle(R.string.builder_menu_load)
            .setItems(files, new DialogInterface.OnClickListener() {
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
