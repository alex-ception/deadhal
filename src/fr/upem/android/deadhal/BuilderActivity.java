package fr.upem.android.deadhal;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.Switch;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import fr.upem.android.deadhal.dialog.SaveDialogFragment;
import fr.upem.android.deadhal.maze.Maze;
import fr.upem.android.deadhal.maze.XMLWriter;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;

public class BuilderActivity extends FragmentActivity implements SaveDialogFragment.SaveDialogListener
{
    private Maze maze;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);

        this.maze = new Maze();
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
        Switch sw = new Switch(getApplicationContext());
        sw.setText("Rotation : ");
        sw.setTextColor(Color.BLACK);
        BuilderView mView = new BuilderView(this,sw);
        linearLayout.addView(sw);
        linearLayout.addView(mView);
        
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.builder, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()) {
            case R.id.action_room:
                return true;
            case R.id.action_interest:
                return true;
            case R.id.action_io:
                return true;
            case R.id.action_save:
                Log.v("DH", "saving");
                this.saveAction();
                Log.v("DH", "saved");
                return true;
            case R.id.action_load:
                try {
                    this.loadAction();
                    Log.v("DH", "loaded");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SAXException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            case R.id.action_menu:
                Intent builder = new Intent(this, MenuMainActivity.class);
                this.startActivityForResult(builder, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveAction()
    {
        DialogFragment saveDialog = new SaveDialogFragment();
        saveDialog.show(this.getFragmentManager(), "SaveDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(SaveDialogFragment dialog)
    {
        String levelName = dialog.getLevelName();
        if (levelName.length() == 0)
            levelName = "maze";

        this.maze.setName(levelName);

        try {
            XMLWriter xmlWriter = new XMLWriter(this.maze);
            FileOutputStream fp = this.openFileOutput(xmlWriter.getFileName(), Context.MODE_PRIVATE);
            fp.write(xmlWriter.getContent());
            fp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDialogNegativeClick(SaveDialogFragment dialog)
    {
    }

    public void loadAction() throws IOException, ParserConfigurationException, SAXException
    {
        InputStream fp = this.openFileInput("maze.xml");
        if (fp == null)
            return;

        String temp;
        InputStreamReader isr   = new InputStreamReader(fp);
        BufferedReader br       = new BufferedReader(isr);
        StringBuilder content   = new StringBuilder();

        while ((temp = br.readLine()) != null)
            content.append(temp);

        Log.v("DH", content.toString());
//        XMLReader xmlReader = new XMLReader(content.toString());
    }
}
