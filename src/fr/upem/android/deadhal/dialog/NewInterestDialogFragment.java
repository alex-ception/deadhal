package fr.upem.android.deadhal.dialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import fr.upem.android.deadhal.BuilderActivity;
import fr.upem.android.deadhal.R;
import fr.upem.android.deadhal.maze.Interest;
import fr.upem.android.deadhal.maze.Maze;
import fr.upem.android.deadhal.maze.Room;

/**
 * A class representing a dialog for a new interest
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class NewInterestDialogFragment extends DialogFragment
{
    /**
     * An interface representing the contract to follow for the listener
     */
    public interface NewInterestDialogListener
    {
        public void onDialogPositiveClick(NewInterestDialogFragment dialog, Room room, Interest interest);
        public void onDialogNegativeClick(NewInterestDialogFragment dialog);
    }

    private NewInterestDialogListener listener;

    /**
     * {@inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater inflater             = this.getActivity().getLayoutInflater();
        View view                           = inflater.inflate(R.layout.dialog_new_interest, null);
        final Maze maze                     = ((BuilderActivity) this.getActivity()).getMaze();
        final TreeMap<String, String> rooms = maze.getRoomsByIdAndName();
        final AlertDialog.Builder alert     = new AlertDialog.Builder(this.getActivity());
        final EditText interestName         = (EditText) view.findViewById(R.id.interest_name);
        final Spinner room                  = (Spinner) view.findViewById(R.id.interest_room);

        this.fillSpinner(room, rooms.values());

        alert
            .setTitle(R.string.builder_menu_interest)
            .setView(view)
            .setPositiveButton(R.string.builder_save_level_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Room roomObject = maze.getRoomById((String) rooms.keySet().toArray()[(int) room.getSelectedItemId()]);
                    listener.onDialogPositiveClick(NewInterestDialogFragment.this, roomObject, new Interest(interestName.getText().toString()));
                }
            })
            .setNegativeButton(R.string.builder_save_level_nok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    listener.onDialogNegativeClick(NewInterestDialogFragment.this);
                }
            });
    
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
            this.listener = (NewInterestDialogListener) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NewInterestDialogListener");
        }
    }

    /**
     * Fills a spinner
     * 
     * @param spinner The spinner to fill
     * @param values The values to fill with
     */
    private void fillSpinner(Spinner spinner, Collection<String> values)
    {
        ArrayList<String> valuesAL = new ArrayList<String>();
        valuesAL.addAll(values);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                valuesAL
        );
        spinner.setAdapter(adapter);
    }
}
