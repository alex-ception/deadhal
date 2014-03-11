package fr.upem.android.deadhal;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;
import fr.upem.android.deadhal.dialog.LoadDialogFragment;
import fr.upem.android.deadhal.dialog.NewIODialogFragment;
import fr.upem.android.deadhal.dialog.NewRoomDialogFragment;
import fr.upem.android.deadhal.dialog.SaveDialogFragment;
import fr.upem.android.deadhal.maze.Maze;
import fr.upem.android.deadhal.maze.Room;
import fr.upem.android.deadhal.maze.XMLReader;
import fr.upem.android.deadhal.maze.XMLWriter;
import fr.upem.android.deadhal.utils.MazeBuilder;

/**
 * Controller handling logic for the level builder
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class BuilderActivity extends FragmentActivity
implements
    SaveDialogFragment.SaveDialogListener,
    LoadDialogFragment.LoadDialogListener,
    NewRoomDialogFragment.NewRoomDialogListener,
    NewIODialogFragment.NewIODialogListener
{
    /**
     * The object representing the maze
     */
    private Maze maze;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);

        this.maze = new Maze();
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
        BuilderView mView = new BuilderView(this);
        linearLayout.addView(mView);
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.builder, menu);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()) {
            case R.id.action_room:
                this.newRoomAction();
                return true;
            case R.id.action_interest:
                return true;
            case R.id.action_io:
                this.newIOAction();
                return true;
            case R.id.action_save:
                this.saveAction();
                return true;
            case R.id.action_load:
                this.loadAction();
                return true;
            case R.id.action_menu:
                Intent builder = new Intent(this, MenuMainActivity.class);
                this.startActivityForResult(builder, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Handles the call of the save dialog
     * 
     * @return void
     */
    public void saveAction()
    {
        DialogFragment saveDialog = new SaveDialogFragment();
        saveDialog.show(this.getFragmentManager(), "SaveDialogFragment");
    }

    /**
     * Handles the OK button when trying to save a new maze through dialog
     * Saves the maze if everything is OK
     * Shows a toast and recalls the dialog if the room name if empty or there was a error while saving
     * 
     * @param dialog The dialog fragment created
     * @param roomName The name of the maze to save
     * 
     * @return void
     */
    @Override
    public void onDialogPositiveClick(SaveDialogFragment dialog, String levelName)
    {
        if (levelName.length() == 0) {
            Toast
                .makeText(this.getApplicationContext(), R.string.builder_save_level_name_empty, Toast.LENGTH_LONG)
                .show()
            ;
            this.saveAction();
    
            return;
        }

        this.maze.setName(levelName);

        try {
            XMLWriter xmlWriter = new XMLWriter(this.maze);
            FileOutputStream fp = this.openFileOutput(xmlWriter.getFileName(), Context.MODE_PRIVATE);
            Log.e("DH", "Save to " + xmlWriter.getFileName());
            Log.e("DH", xmlWriter.getContent());
            fp.write(xmlWriter.getContent().getBytes());
            fp.close();
        } catch (Exception e) {
            this.onDialogNegativeClick(dialog);
            this.saveAction();
        }
    }


    /**
     * Handles the cancel button when trying to save a maze through dialog
     * Shows a Toast saying the level was not saved
     * 
     * @param dialog The dialog fragment created
     * 
     * @return void
     */
    @Override
    public void onDialogNegativeClick(SaveDialogFragment dialog)
    {
        Toast
            .makeText(this.getApplicationContext(), R.string.builder_save_level_error, Toast.LENGTH_LONG)
            .show()
        ;
    }

    /**
     * Handles the call of the load dialog
     * 
     * @return void
     */
    public void loadAction()
    {
        DialogFragment loadDialog = new LoadDialogFragment();
        loadDialog.show(this.getFragmentManager(), "LoadDialogFragment");
    }

    /**
     * Handles the OK button when trying to load a new maze through dialog
     * Loads the maze if everything is OK
     * Calls cancel button handler if the level name was not properly filled and recalls the dialog
     * 
     * @param dialog The dialog fragment created
     * @param roomName The name of the maze to load
     * 
     * @return void
     */
    @Override
    public void onDialogPositiveClick(LoadDialogFragment dialog, String levelName)
    {
        InputStream fp;
        try {
            fp = this.openFileInput(levelName);

            String temp;
            InputStreamReader isr   = new InputStreamReader(fp);
            BufferedReader br       = new BufferedReader(isr);
            StringBuilder content   = new StringBuilder();

            while ((temp = br.readLine()) != null)
                content.append(temp);
            XMLReader xmlReader = new XMLReader(content.toString());
            this.maze = xmlReader.getMaze();
        } catch (Exception e) {
            this.onDialogNegativeClick(dialog);
            this.loadAction();
        }
    }


    /**
     * Handles the cancel button when trying to load a maze through dialog
     * Shows a Toast saying the level was not loaded
     * 
     * @param dialog The dialog fragment created
     * 
     * @return void
     */
    @Override
    public void onDialogNegativeClick(LoadDialogFragment dialog)
    {
        Toast
            .makeText(this.getApplicationContext(), R.string.builder_load_level_error, Toast.LENGTH_LONG)
            .show()
        ;
    }

    /**
     * Handles the call of a new room dialog
     * 
     * @return void
     */
    public void newRoomAction()
    {
        DialogFragment loadDialog = new NewRoomDialogFragment();
        loadDialog.show(this.getFragmentManager(), "NewRoomDialogFragment");
    }

    /**
     * Handles the OK button when trying to add a new room through dialog
     * Adds the room to the maze if everything is OK
     * Shows a toast and recalls the dialog if the room name if empty or already referenced
     * 
     * @param dialog The dialog fragment created
     * @param roomName The name of the room to add
     * 
     * @return void
     */
    @Override
    public void onDialogPositiveClick(NewRoomDialogFragment dialog, String roomName)
    {
        if (roomName.length() == 0) {
            Toast
                .makeText(this.getApplicationContext(), R.string.builder_room_name_empty, Toast.LENGTH_LONG)
                .show()
            ;
            this.newRoomAction();

            return;
        }

        try {
            this.maze.addRoom(MazeBuilder.newRoom(roomName));
        } catch (RuntimeException e) {
            Toast
                .makeText(this.getApplicationContext(), R.string.builder_room_already_referenced, Toast.LENGTH_LONG)
                .show()
            ;
            this.newIOAction();
        }
    }

    /**
     * Handles the cancel button when trying to add a new room through dialog
     * Shows a Toast saying the room was not added
     * 
     * @param dialog The dialog fragment created
     * 
     * @return void
     */
    @Override
    public void onDialogNegativeClick(NewRoomDialogFragment dialog)
    {
        Toast
            .makeText(this.getApplicationContext(), R.string.builder_room_error, Toast.LENGTH_LONG)
            .show()
        ;
    }

    /**
     * Handles the call of a new IO dialog
     * Aborts and show a Toast if there are not at least two rooms
     * 
     * @return void
     */
    public void newIOAction()
    {
        if (this.maze.getRooms().size() < 2) {
            Toast
                .makeText(this.getApplicationContext(), R.string.builder_io_not_enough_room, Toast.LENGTH_LONG)
                .show()
            ;

            return;
        }

        DialogFragment loadDialog = new NewIODialogFragment();
        loadDialog.show(this.getFragmentManager(), "NewIODialogFragment");
    }

    /**
     * Handles the OK button when trying to add a new IO through dialog
     * Adds the IO to the maze if everything is OK
     * Shows a toast and recalls the dialog if the IO is a loop on the same room
     * 
     * @param dialog The dialog fragment created
     * @param from The room where the IO starts
     * @param directionFrom The position of the IO on the room
     * @param to The room where the IO ends
     * @param directionTo The position of the IO on the room
     * @param twoWay Defines if the IO a two-way IO
     * 
     * @return void
     */
    @Override
    public void onDialogPositiveClick(NewIODialogFragment dialog, Room from, int directionFrom, Room to, int directionTo, boolean twoWay)
    {
        if (from.getId() == to.getId()) {
            Toast
                .makeText(this.getApplicationContext(), R.string.builder_io_same_room, Toast.LENGTH_LONG)
                .show()
            ;

            this.newIOAction();
            return;
        }

        MazeBuilder.newIo(from, directionFrom, to, directionTo, twoWay);
    }

    /**
     * Handles the cancel button when trying to add a IO through dialog
     * Shows a Toast saying the IO was not added
     * 
     * @param dialog The dialog fragment created
     * 
     * @return void
     */
    @Override
    public void onDialogNegativeClick(NewIODialogFragment dialog)
    {
        Toast
            .makeText(this.getApplicationContext(), R.string.builder_io_error, Toast.LENGTH_LONG)
            .show()
        ;
    }

    /**
     * Returns the maze

     * @return the maze
     */
	public Maze getMaze()
	{
		return this.maze;
	}
}
