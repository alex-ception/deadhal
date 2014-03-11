package fr.upem.android.deadhal.dialog;

import fr.upem.android.deadhal.R;
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

public class NewIODialogFragment extends DialogFragment
{
    public interface NewIODialogListener
    {
        public void onDialogPositiveClick(NewIODialogFragment dialog, String from, String to, boolean twoWay);
        public void onDialogNegativeClick(NewIODialogFragment dialog);
    }

    private NewIODialogListener listener;

    /**
     * {@inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater inflater         = this.getActivity().getLayoutInflater();
        View view                       = inflater.inflate(R.layout.dialog_new_io, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this.getActivity());
        final Spinner fromDirection     = (Spinner) view.findViewById(R.id.from_direction);
        final Spinner fromRoom          = (Spinner) view.findViewById(R.id.from_room);
        final Spinner toDirection       = (Spinner) view.findViewById(R.id.to_direction);
        final Spinner toRoom            = (Spinner) view.findViewById(R.id.to_room);
        final Switch twoWay             = (Switch) view.findViewById(R.id.two_way);

        this.fillDirection(fromDirection);
        this.fillDirection(toDirection);

        alert
            .setTitle(R.string.builder_menu_io)
            .setView(view)
            .setPositiveButton(R.string.builder_save_level_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
//                    listener.onDialogPositiveClick(NewIODialogFragment.this, levelName.getText().toString());
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

    private void fillSpinner(Spinner spinner)
    {
        
    }

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
