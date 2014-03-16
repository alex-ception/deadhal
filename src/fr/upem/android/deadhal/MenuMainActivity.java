package fr.upem.android.deadhal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import fr.upem.android.deadhal.dialog.LoadDialogFragment;
import fr.upem.android.deadhal.dialog.LoadDialogFragment.LoadDialogListener;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;
import android.widget.Toast;

/**
 * Controller handling logic for the main menu
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class MenuMainActivity extends Activity implements LoadDialogListener
{
    private ShareActionProvider share;

    /**
     * {@inheritDoc}
     */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_main);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);

		MenuItem item = menu.findItem(R.id.menu_item_share);
		this.share    = (ShareActionProvider) item.getActionProvider();

		return true;
	}

	/**
	 * Handle a click on a button
	 * 
	 * @param view The button clicked
	 */
	public void buttonsOnClick(View view)
	{
	    switch (view.getId()) {
		    case R.id.menu_main_button_builder:
	            Intent builder = new Intent(this, BuilderActivity.class);
	            this.startActivityForResult(builder, 0);
	            break;
		    case R.id.menu_main_button_game:
	            Intent game = new Intent(this, GameActivity.class);
	            this.startActivityForResult(game, 0);
	            break;
		    case R.id.menu_main_button_share:
		        DialogFragment loadDialog = new LoadDialogFragment();
		        loadDialog.show(this.getFragmentManager(), "LoadDialogFragment");
		        break;
	        case R.id.menu_main_button_quit:
	            this.finish();
	            System.exit(0);
            default:
                break;
	    }
	}

	/**
	 * Sets the shareIntent on share action provider
	 * 
	 * @param shareIntent The share intent
	 */
	private void setShareIntent(Intent shareIntent)
	{
	    if (this.share == null)
	        return;

	    this.share.setShareIntent(shareIntent);
	}

	/**
	 * Create the share intent allowing to send XML maze
	 * @param levelName The file name
	 * @return The share intent created
	 */
	private Intent createShareIntent(String levelName)
	{
	    Intent shareIntent = new Intent(Intent.ACTION_SEND);
	    shareIntent
	        .setType("text/plain")
	        .putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), levelName)))
	    ;

	    return shareIntent;
	}

    /**
     * Handles the OK button when trying to share a maze through dialog
     * Share the maze if everything is OK
     * 
     * @param dialog The dialog fragment created
     * @param roomName The name of the maze to load
     */
    @Override
    public void onDialogPositiveClick(LoadDialogFragment dialog, String levelName) throws FileNotFoundException, IOException
    {
        this.setShareIntent(this.createShareIntent(levelName));
    }

    /**
     * Handles the cancel button when trying to share a maze through dialog
     * Shows a Toast saying the level was not loaded
     * 
     * @param dialog The dialog fragment created
     */
    @Override
    public void onDialogNegativeClick(LoadDialogFragment dialog)
    {
        Toast
            .makeText(this.getApplicationContext(), R.string.menu_main_share_error, Toast.LENGTH_LONG)
            .show()
        ;
    }
}
