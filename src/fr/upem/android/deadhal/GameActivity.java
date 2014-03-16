package fr.upem.android.deadhal;

import fr.upem.android.deadhal.dialog.LoadDialogFragment;
import fr.upem.android.deadhal.maze.Maze;
import fr.upem.android.deadhal.maze.XMLReader;
import fr.upem.android.deadhal.utils.ExternalStorageIO;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Controller handling logic for the game
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class GameActivity extends FragmentActivity implements LoadDialogFragment.LoadDialogListener
{
    private Maze maze;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.maze                   = new Maze();
        LinearLayout linearLayout   = (LinearLayout)findViewById(R.id.linearLayoutGame);
        GameView mView              = new GameView(this);
        linearLayout.addView(mView);
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()) {
            case R.id.action_load:
                this.loadAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        try {
            Log.v("DH", ExternalStorageIO.load(levelName));
            XMLReader xmlReader = new XMLReader(this.maze, ExternalStorageIO.load(levelName));
            this.maze = xmlReader.getMaze();
            
            CharSequence text = "Clic on first Room";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
            
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
     * Returns the maze

     * @return the maze
     */
	public Maze getMaze()
	{
		return this.maze;
	}
}
