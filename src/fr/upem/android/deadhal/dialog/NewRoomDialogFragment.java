package fr.upem.android.deadhal.dialog;

import fr.upem.android.deadhal.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

public class NewRoomDialogFragment extends DialogFragment
{
    public interface NewRoomDialogListener
    {
        public void onDialogPositiveClick(NewRoomDialogFragment dialog, String roomName);
        public void onDialogNegativeClick(NewRoomDialogFragment dialog);
    }

    private NewRoomDialogListener listener;
    private EditText roomName;

    /**
     * {@inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        this.roomName                   = new EditText(this.getActivity());
        final AlertDialog.Builder alert = new AlertDialog.Builder(this.getActivity());

        alert
            .setTitle(R.string.builder_menu_room)
            .setMessage(R.string.builder_room_name)
            .setView(this.roomName)
            .setPositiveButton(R.string.builder_room_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    listener.onDialogPositiveClick(NewRoomDialogFragment.this, roomName.getText().toString());
                }
            })
            .setNegativeButton(R.string.builder_room_nok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    listener.onDialogNegativeClick(NewRoomDialogFragment.this);
                }
            });
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
            this.listener = (NewRoomDialogListener) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NewRoomDialogListener");
        }
    }
}
