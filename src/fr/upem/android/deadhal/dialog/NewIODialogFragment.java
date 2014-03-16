package fr.upem.android.deadhal.dialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.TreeMap;

import fr.upem.android.deadhal.BuilderActivity;
import fr.upem.android.deadhal.R;
import fr.upem.android.deadhal.maze.Direction;
import fr.upem.android.deadhal.maze.Maze;
import fr.upem.android.deadhal.maze.Room;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

@SuppressLint("DefaultLocale")
/**
 * A class representing a dialog for a new IO
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class NewIODialogFragment extends DialogFragment
{
    /**
     * An interface representing the contract to follow for the listener
     */
    public interface NewIODialogListener
    {
        public void onDialogPositiveClick(NewIODialogFragment dialog, Room from, int directionFrom, Room to, int directionTo, boolean twoWay);
        public void onDialogNegativeClick(NewIODialogFragment dialog);
    }

    /**
     * 
     */
    private NewIODialogListener listener;

    /**
     * {@inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater inflater             = this.getActivity().getLayoutInflater();
        View view                           = inflater.inflate(R.layout.dialog_new_io, null);
        final Maze maze                     = ((BuilderActivity) this.getActivity()).getMaze();
        final TreeMap<String, String> rooms = maze.getRoomsByIdAndName();
        final AlertDialog.Builder alert     = new AlertDialog.Builder(this.getActivity());
        final Spinner fromDirection         = (Spinner) view.findViewById(R.id.from_direction);
        final Spinner fromRoom              = (Spinner) view.findViewById(R.id.from_room);
        final Spinner toDirection           = (Spinner) view.findViewById(R.id.to_direction);
        final Spinner toRoom                = (Spinner) view.findViewById(R.id.to_room);
        final Switch twoWay                 = (Switch) view.findViewById(R.id.two_way);

        this.fillDirection(fromDirection);
        this.fillDirection(toDirection);
        this.fillSpinner(fromRoom, rooms.values());
        this.fillSpinner(toRoom, rooms.values());

        alert
            .setTitle(R.string.builder_menu_io)
            .setView(view)
            .setPositiveButton(R.string.builder_save_level_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Room fromRoomObject     = maze.getRoomById((String) rooms.keySet().toArray()[(int) fromRoom.getSelectedItemId()]);
                    Room toRoomObject       = maze.getRoomById((String) rooms.keySet().toArray()[(int) toRoom.getSelectedItemId()]);
                    String fromDirectionStr = ((String) fromDirection.getSelectedItem()).toLowerCase(Locale.ENGLISH);
                    String toDirectionStr   = ((String) toDirection.getSelectedItem()).toLowerCase(Locale.ENGLISH);

                    listener.onDialogPositiveClick(
                            NewIODialogFragment.this,
                            fromRoomObject,
                            Direction.getDirectionToInt(fromDirectionStr),
                            toRoomObject,
                            Direction.getDirectionToInt(toDirectionStr),
                            twoWay.isChecked()
                    );
                }
            })
            .setNegativeButton(R.string.builder_save_level_nok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    listener.onDialogNegativeClick(NewIODialogFragment.this);
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
            this.listener = (NewIODialogListener) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NewIODialogListener");
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

    /**
     * Fills the directions spinners
     * 
     * @param spinner The spinner to fill
     */
    private void fillDirection(Spinner spinner)
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this.getActivity(),
                R.array.directions_values,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
